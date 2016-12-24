package com.ysu.zyw.tc.components.cache.redis;

import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.collect.Sets;
import com.ysu.zyw.tc.base.ex.TcException;
import com.ysu.zyw.tc.base.utils.TcUtils;
import com.ysu.zyw.tc.components.cache.api.TcOpsForGroupedValue;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

import static com.google.common.base.Preconditions.checkNotNull;

@Slf4j
public class TcRedisOpsForGroupedValue implements TcOpsForGroupedValue {

    protected static final String GROUP_FIELD_PREFIX = "GROUP_";

    protected static final String GROUP_NAME_KEY_SPLIT = ":";

    @Getter
    @Setter
    private long tryLockTimeout;

    @Getter
    @Setter
    protected RedisTemplate<String, Object> redisTemplate;

    protected String buildGroupedKey(@Nonnull String group, @Nonnull String key) {
        checkNotNull(group, "empty group is not allowed");
        checkNotNull(key, "empty key is not allowed");
        return GROUP_FIELD_PREFIX + group + GROUP_NAME_KEY_SPLIT + key;
    }

    @Override
    public void set(@Nonnull String group,
                    @Nonnull String key,
                    @Nonnull Object value,
                    long timeout) {
        checkNotNull(group, "empty group is not allowed");
        checkNotNull(key, "empty key is not allowed");
        checkNotNull(value, "null value is not allowed");
        String groupedKey = buildGroupedKey(group, key);
        redisTemplate.opsForValue().set(groupedKey, value, timeout, TimeUnit.MILLISECONDS);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T get(@Nonnull String group,
                     @Nonnull String key,
                     @Nonnull TypeReference<T> typeReference) {
        checkNotNull(group, "empty group is not allowed");
        checkNotNull(key, "empty key is not allowed");
        checkNotNull(typeReference, "null clazz is not allowed");
        String groupedKey = buildGroupedKey(group, key);
        return (T) redisTemplate.opsForValue().get(groupedKey);
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
    @SuppressWarnings({"Duplicates", "unchecked"})
    @Override
    public <T> T get(@Nonnull String group,
                     @Nonnull String key,
                     @Nonnull Callable<T> valueLoader,
                     long timeout,
                     @Nullable final ReentrantLock lock) {
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
            return value;
        } else {
            if (Objects.isNull(lock)) {
                return loadValueByValueLoaderAndCacheIt(group, key, groupedKey, valueLoader, timeout);
            } else {
                try {
                    lock.tryLock(tryLockTimeout, TimeUnit.MILLISECONDS);
                    return loadValue(group, key, valueLoader, timeout, groupedKey);
                } catch (InterruptedException e) {
                    throw new TcException("load cache blocked, throw exception");
                } finally {
                    lock.unlock();
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    private <T> T loadValue(@Nonnull String group,
                            @Nonnull String key,
                            @Nonnull Callable<T> valueLoader,
                            long timeout,
                            @Nonnull String groupedKey) {
        // lock and get
        T sValue = TcUtils.doQuietly(() -> (T) redisTemplate.opsForValue().get(groupedKey), null);
        if (Objects.nonNull(sValue)) {
            return sValue;
        }
        // not found, try load value.
        return loadValueByValueLoaderAndCacheIt(group, key, groupedKey, valueLoader, timeout);
    }

    @SuppressWarnings("Duplicates")
    private <T> T loadValueByValueLoaderAndCacheIt(@Nonnull String group,
                                                   @Nonnull String key,
                                                   @Nonnull String groupedKey,
                                                   @Nonnull Callable<T> valueLoader,
                                                   long timeout) {
        T loadedValue;
        try {
            loadedValue = valueLoader.call();
        } catch (Exception e) {
            throw new TcException(e, groupedKey, valueLoader);
        }
        checkNotNull(loadedValue, "empty loaded value is not allowed");
        TcUtils.doQuietly(() ->
                redisTemplate.opsForValue().set(groupedKey, loadedValue, timeout, TimeUnit.MILLISECONDS));
        return loadedValue;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean exists(@Nonnull String group,
                          @Nonnull String key) {
        checkNotNull(group, "empty group is not allowed");
        checkNotNull(key, "empty key is not allowed");
        return redisTemplate.execute((RedisCallback<Boolean>) connection -> connection
                .exists(((RedisSerializer<String>) redisTemplate.getKeySerializer())
                        .serialize(buildGroupedKey(group, key))));
    }

    @Override
    public void expire(@Nonnull String group,
                       @Nonnull String key,
                       long timeout) {
        checkNotNull(group, "empty group is not allowed");
        checkNotNull(key, "empty key is not allowed");
        String groupedKey = buildGroupedKey(group, key);
        redisTemplate.expire(groupedKey, timeout, TimeUnit.MILLISECONDS);
    }

    @Override
    public void delete(@Nonnull String group,
                       @Nonnull String key) {
        checkNotNull(group, "empty group is not allowed");
        checkNotNull(key, "empty key is not allowed");
        String groupedKey = buildGroupedKey(group, key);
        redisTemplate.delete(groupedKey);
    }

    @Override
    public Set<String> keys(@Nonnull String group) {
        checkNotNull(group, "empty group is not allowed");
        if (redisTemplate.getKeySerializer() instanceof JdkSerializationRedisSerializer) {
            log.warn("object key serialization(jdk serialization) strategy do not support keys command");
            return Sets.newHashSet();
        }
        return redisTemplate.keys(GROUP_FIELD_PREFIX + group + GROUP_NAME_KEY_SPLIT + "*")
                .stream()
                .map(key ->
                        key.split(GROUP_FIELD_PREFIX + group + GROUP_NAME_KEY_SPLIT)[1]
                ).collect(Collectors.toSet());
    }

    @Override
    public void delete(@Nonnull String group) {
        checkNotNull(group, "empty group is not allowed");
        Set<String> keys = redisTemplate.keys(GROUP_FIELD_PREFIX + group + GROUP_NAME_KEY_SPLIT + "*");
        redisTemplate.delete(keys);
    }

}
