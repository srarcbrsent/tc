package com.ysu.zyw.tc.components.cache.codis.ops;

import com.ysu.zyw.tc.sys.ex.TcException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.serializer.RedisSerializer;

import javax.annotation.Nonnull;
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
 * do not influenced.
 */
@Slf4j
public class TcOpsForGroupedValue extends TcAbstractOpsForGroup {

    public void set(@Nonnull String group,
                    @Nonnull String key,
                    @Nonnull Serializable value,
                    long timeout) {
        checkNotNull(group, "empty group is not allowed");
        checkNotNull(key, "empty key is not allowed");
        checkNotNull(value, "null value is not allowed");
        String groupedKey = buildGroupedKey(group, key);
        codisTemplate.opsForValue().set(groupedKey, value, timeout, TimeUnit.MILLISECONDS);
        codisTemplate.opsForZSet().add(group, groupedKey, buildGroupedZSetFieldScore(timeout));
        if (log.isDebugEnabled()) {
            log.debug("cache set key [{}], value [{}], timeout [{}]", groupedKey, value, timeout);
        }
    }

    @SuppressWarnings(value = "unchecked")
    public <T extends Serializable> T get(@Nonnull String group,
                                          @Nonnull String key,
                                          @Nonnull Class<T> clazz) {
        checkNotNull(group, "empty group is not allowed");
        checkNotNull(key, "empty key is not allowed");
        checkNotNull(clazz, "null clazz is not allowed");
        String groupedKey = buildGroupedKey(group, key);
        T sValue = (T) codisTemplate.opsForValue().get(groupedKey);
        if (Objects.isNull(sValue)) {
            codisTemplate.opsForZSet().remove(group, groupedKey);
            if (codisTemplate.opsForZSet().size(group) == 0) {
                codisTemplate.delete(group);
            }
        }
        if (log.isDebugEnabled()) {
            log.debug("cache get key [{}], value [{}], clazz [{}]", groupedKey, sValue, clazz);
        }
        return sValue;
    }

    @SuppressWarnings(value = "unchecked")
    public <T extends Serializable> T get(@Nonnull String group,
                                          @Nonnull String key,
                                          @Nonnull Callable<T> valueLoader,
                                          long timeout) {
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
            value = (T) codisTemplate.opsForValue().get(groupedKey);
        } catch (Exception e) {
            log.error("", e);
            return loadValue(group, key, groupedKey, valueLoader, timeout);
        }
        if (Objects.nonNull(value)) {
            if (log.isDebugEnabled()) {
                log.debug("get object [{}] from cache by key [{}]", value, key);
            }
            return value;
        } else {
            // FIXME if the key is a dynamic key(the key has so many enumerated values), this place may lead
            // to constant pool memory leak, this place we suppose the key is not inconstancy
            synchronized (groupedKey.intern()) {
                // lock and get
                T sValue = null;
                try {
                    sValue = (T) codisTemplate.opsForValue().get(groupedKey);
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
                return loadValue(group, key, groupedKey, valueLoader, timeout);
            }
        }
    }

    private <T extends Serializable> T loadValue(@Nonnull String group,
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
            codisTemplate.opsForValue().set(groupedKey, loadedValue, timeout, TimeUnit.MILLISECONDS);
            codisTemplate.opsForZSet().add(group, groupedKey, buildGroupedZSetFieldScore(timeout));
        } catch (Exception e) {
            // if cache failed, let the load value succ.
            log.error("", e);
        }
        if (log.isDebugEnabled()) {
            log.debug("put object [{}] into cache by key [{}], timeout [{}]", loadedValue, key, timeout);
        }
        return loadedValue;
    }

    @SuppressWarnings(value = "unchecked")
    public boolean exists(@Nonnull String group, @Nonnull String key) {
        checkNotNull(group, "empty group is not allowed");
        checkNotNull(key, "empty key is not allowed");
        return codisTemplate.execute((RedisCallback<Boolean>) connection -> connection
                .exists(((RedisSerializer<String>) codisTemplate.getKeySerializer())
                        .serialize(buildGroupedKey(group, key))));
    }

    public void expire(@Nonnull String group, @Nonnull String key, long timeout) {
        checkNotNull(group, "empty group is not allowed");
        checkNotNull(key, "empty key is not allowed");
        String groupedKey = buildGroupedKey(group, key);
        codisTemplate.expire(groupedKey, timeout, TimeUnit.MILLISECONDS);
        codisTemplate.opsForZSet().add(group, groupedKey, buildGroupedZSetFieldScore(timeout));
    }

    public void delete(@Nonnull String group, @Nonnull String key) {
        checkNotNull(group, "empty group is not allowed");
        checkNotNull(key, "empty key is not allowed");
        String groupedKey = buildGroupedKey(group, key);
        codisTemplate.delete(groupedKey);
        codisTemplate.opsForZSet().remove(group, groupedKey);
        if (codisTemplate.opsForZSet().size(group) == 0) {
            codisTemplate.delete(group);
        }
        if (log.isDebugEnabled()) {
            log.debug("cache delete key [{}]", groupedKey);
        }
    }

    public Set<String> keys(@Nonnull String group) {
        checkNotNull(group, "empty group is not allowed");
        long now = new Date().getTime();
        delete(group, 0, now);
        Set<Serializable> sValidKeys = codisTemplate.opsForZSet().rangeByScore(group, now, Long.MAX_VALUE);
        return sValidKeys.parallelStream().map(sValidKey -> (String) sValidKey)
                .map(validKey -> validKey.split(GROUP_NAME_KEY_SPLIT)[2]).collect(Collectors.toSet());
    }

    public void delete(@Nonnull String group) {
        checkNotNull(group, "empty group is not allowed");
        delete(group, 0, Long.MAX_VALUE);
    }

}
