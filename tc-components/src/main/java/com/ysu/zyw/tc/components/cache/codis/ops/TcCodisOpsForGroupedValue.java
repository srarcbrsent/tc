package com.ysu.zyw.tc.components.cache.codis.ops;

import com.ysu.zyw.tc.sys.ex.TcException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.serializer.RedisSerializer;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author yaowu.zhang
 * @warn this group apiimpl is highly depends on the time in distribution system jvm.
 * because the ops for value use the relative time as timeout, but the zset use the
 * absolute time to count the relation of values, so if the time in the cluster is
 * scattered, then the zset score will not match the real timeout, and the apiimpl
 * keys will be not perfect match, but the other apiimpl is use the relative time so
 * do not influenced. (we already add some offset into it, plz configure overtime)
 */
@Slf4j
public class TcCodisOpsForGroupedValue extends TcAbstractCodisOpsForGroup {

    @Override
    public void set(@Nonnull String group,
                    @Nonnull String key,
                    @Nonnull Serializable value,
                    long timeout) {
        checkNotNull(group, "empty group is not allowed");
        checkNotNull(key, "empty key is not allowed");
        checkNotNull(value, "null value is not allowed");
        String groupedKey = buildGroupedKey(group, key);
        redisTemplate.opsForValue().set(groupedKey, value, timeout, TimeUnit.MILLISECONDS);
        redisTemplate.opsForZSet().add(group, groupedKey, buildGroupedZSetFieldScore(timeout));
        if (log.isDebugEnabled()) {
            log.debug("cache set key [{}], value [{}], timeout [{}]", groupedKey, value, timeout);
        }
    }

    @Override
    @SuppressWarnings(value = "unchecked")
    public <T> T get(@Nonnull String group,
                     @Nonnull String key,
                     @Nonnull Class<T> clazz) {
        checkNotNull(group, "empty group is not allowed");
        checkNotNull(key, "empty key is not allowed");
        checkNotNull(clazz, "null clazz is not allowed");
        String groupedKey = buildGroupedKey(group, key);
        T sValue = (T) redisTemplate.opsForValue().get(groupedKey);
        if (log.isDebugEnabled()) {
            log.debug("cache get key [{}], value [{}], clazz [{}]", groupedKey, sValue, clazz);
        }
        return sValue;
    }

    // this method requires a parameter named 'lock', it means if it is not null, we will lock it and
    // try to load value, otherwise we will directly load value by value loader.
    // we suppose that there may have multiple thread share one cache key and this key will be get
    // concurrently, then if this key expired, there may have multi thread pass through cache layer
    // and going to call value loader, so if this logic is just like this, you should provide a lock
    // and we will use this lock to lock the value loader call.
    // and on another hand, if there is no more thread shared one cache key or there is no more thread
    // call this get method concurrently, you should not give a lock and all request will go through
    // concurrently.
    @Override
    @SuppressWarnings(value = {"unchecked", "Duplicates", "SynchronizationOnLocalVariableOrMethodParameter"})
    public <T> T get(@Nonnull String group,
                     @Nonnull String key,
                     @Nonnull Callable<T> valueLoader,
                     long timeout,
                     @Nullable final Object lock) {
        // special, other apiimpl if the cache service itself is offline, they may throw an exception(such
        // as JodisPool is empty), but this apiimpl is different, because this apiimpl means load by cache, if
        // not loaded, then load by value loader, this not loaded include the cache is not exists and
        // also the cache service itself is offline. so it will catch the other exception.
        checkNotNull(group, "empty group is not allowed");
        checkNotNull(key, "null key is not allowed");
        checkNotNull(valueLoader, "null value loader is not allowed");
        String groupedKey = buildGroupedKey(group, key);
        T value;
        try {
            value = (T) redisTemplate.opsForValue().get(groupedKey);
        } catch (Exception e) {
            log.error("", e);
            return loadValueByValueLoaderAndCacheIt(group, key, groupedKey, valueLoader, timeout);
        }
        if (Objects.nonNull(value)) {
            if (log.isDebugEnabled()) {
                log.debug("get object [{}] from cache by key [{}]", value, key);
            }
            return value;
        } else {
            if (Objects.isNull(lock)) {
                return loadValueByValueLoaderAndCacheIt(group, key, groupedKey, valueLoader, timeout);
            } else {
                synchronized (lock) {
                    return loadValue(group, key, groupedKey, valueLoader, timeout);
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    private <T> T loadValue(@Nonnull String group,
                            @Nonnull String key,
                            String groupedKey,
                            @Nonnull Callable<T> valueLoader,
                            long timeout) {
        // lock and get
        T sValue = null;
        try {
            sValue = (T) redisTemplate.opsForValue().get(groupedKey);
        } catch (Exception e) {
            log.error("", e);
        }
        if (Objects.nonNull(sValue)) {
            if (log.isDebugEnabled()) {
                log.debug("get object [{}] from cache by key [{}]", sValue, key);
            }
            return sValue;
        }
        // not found, try load value.
        return loadValueByValueLoaderAndCacheIt(group, key, groupedKey, valueLoader, timeout);
    }

    private <T> T loadValueByValueLoaderAndCacheIt(@Nonnull String group,
                                                   @Nonnull String key,
                                                   @Nonnull String groupedKey,
                                                   @Nonnull Callable<T> valueLoader,
                                                   long timeout) {
        T loadedValue;
        try {
            loadedValue = valueLoader.call();
        } catch (Exception e) {
            throw new TcException(e, key, valueLoader);
        }
        checkNotNull(loadedValue, "empty loaded value is not allowed");
        try {
            redisTemplate.opsForValue().set(groupedKey, loadedValue, timeout, TimeUnit.MILLISECONDS);
            redisTemplate.opsForZSet().add(group, groupedKey, buildGroupedZSetFieldScore(timeout));
        } catch (Exception e) {
            // if cache failed, let the load value succ.
            log.error("", e);
        }
        if (log.isDebugEnabled()) {
            log.debug("put object [{}] into cache by key [{}], timeout [{}]", loadedValue, key, timeout);
        }
        return loadedValue;
    }

    @Override
    @SuppressWarnings(value = "unchecked")
    public boolean exists(@Nonnull String group, @Nonnull String key) {
        checkNotNull(group, "empty group is not allowed");
        checkNotNull(key, "empty key is not allowed");
        return redisTemplate.execute((RedisCallback<Boolean>) connection -> connection
                .exists(((RedisSerializer<String>) redisTemplate.getKeySerializer())
                        .serialize(buildGroupedKey(group, key))));
    }

    @Override
    public void expire(@Nonnull String group, @Nonnull String key, long timeout) {
        checkNotNull(group, "empty group is not allowed");
        checkNotNull(key, "empty key is not allowed");
        String groupedKey = buildGroupedKey(group, key);
        redisTemplate.expire(groupedKey, timeout, TimeUnit.MILLISECONDS);
        redisTemplate.opsForZSet().add(group, groupedKey, buildGroupedZSetFieldScore(timeout));
    }

    @Override
    public void delete(@Nonnull String group, @Nonnull String key) {
        checkNotNull(group, "empty group is not allowed");
        checkNotNull(key, "empty key is not allowed");
        String groupedKey = buildGroupedKey(group, key);
        redisTemplate.delete(groupedKey);
        redisTemplate.opsForZSet().remove(group, groupedKey);
        if (log.isDebugEnabled()) {
            log.debug("cache delete key [{}]", groupedKey);
        }
    }

    @Override
    public Set<String> keys(@Nonnull String group) {
        checkNotNull(group, "empty group is not allowed");
        long now = new Date().getTime();
        delete(group, 0, now);
        Set<Object> sValidKeys = redisTemplate.opsForZSet()
                // this place, because all grouped score are add overtime, so the real
                // timeout time is now + overtime, and this place is highly depends on
                // cluster time
                .rangeByScore(group, now + overtime, Long.MAX_VALUE);
        return sValidKeys.parallelStream()
                .map(sValidKey -> (String) sValidKey)
                .map(validKey -> validKey.split(GROUP_NAME_KEY_SPLIT)[2]).collect(Collectors.toSet());
    }

    @Override
    public void delete(@Nonnull String group) {
        checkNotNull(group, "empty group is not allowed");
        delete(group, 0, Long.MAX_VALUE);
    }

}
