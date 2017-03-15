package com.ysu.zyw.tc.components.cache.api;

import com.fasterxml.jackson.core.type.TypeReference;
import com.ysu.zyw.tc.base.ex.TcException;
import com.ysu.zyw.tc.base.utils.TcUtils;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * TcCacheServiceImpl provide codis upper-level operations
 *
 * @author yaowu.zhang
 */
@Slf4j
public class TcCacheServiceImpl implements TcCacheService {

    @Getter
    @Setter
    private RedisTemplate<String, Object> redisTemplate;

    @Getter
    @Setter
    private TcOpsForGroupedValue tcOpsForGroupedValue;

    @Getter
    @Setter
    private long tryLockTimeout;

    @Override
    public <R> R doInCache(@Nonnull Function<RedisTemplate, R> task) {
        return task.apply(redisTemplate);
    }

    @Override
    public void set(@Nonnull String key,
                    @Nonnull Object value,
                    long timeout) {
        checkNotNull(key, "empty key is not allowed");
        checkNotNull(value, "null value is not allowed");
        redisTemplate.opsForValue().set(key, value, timeout, TimeUnit.MILLISECONDS);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T get(@Nonnull String key,
                     @Nonnull TypeReference<T> typeReference) {
        checkNotNull(key, "empty key is not allowed");
        checkNotNull(typeReference, "null clazz is not allowed");
        Object sValue = redisTemplate.opsForValue().get(key);
        return (T) sValue;
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
    @SuppressWarnings({"unchecked"})
    public <T> T get(@Nonnull String key,
                     @Nonnull Callable<T> valueLoader,
                     long timeout,
                     @Nullable final ReentrantLock lock) {
        // special, other apiimpl if the cache service itself is offline, they may throw an exception(such
        // as JodisPool is empty), but this apiimpl is different, because this apiimpl means load by cache, if
        // not loaded, then load by value loader, this not loaded include the cache is not exists and
        // also the cache service itself is offline. so it will catch the other exception, and let the
        // value loader success.
        checkNotNull(key, "null key is not allowed");
        checkNotNull(valueLoader, "null value loader is not allowed");
        T value;
        try {
            value = (T) redisTemplate.opsForValue().get(key);
        } catch (Exception e) {
            log.error("", e);
            return loadValueByValueLoaderAndCacheIt(key, valueLoader, timeout);
        }
        if (Objects.nonNull(value)) {
            return value;
        } else {
            if (Objects.isNull(lock)) {
                return loadValueByValueLoaderAndCacheIt(key, valueLoader, timeout);
            } else {
                try {
                    lock.tryLock(tryLockTimeout, TimeUnit.MILLISECONDS);
                    return loadValue(key, valueLoader, timeout);
                } catch (InterruptedException e) {
                    throw new TcException("load cache blocked, throw exception");
                } finally {
                    lock.unlock();
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    private <T> T loadValue(@Nonnull String key,
                            @Nonnull Callable<T> valueLoader,
                            long timeout) {
        // lock and get
        T sValue = TcUtils.defaultValue(() -> (T) redisTemplate.opsForValue().get(key), () -> null);
        if (Objects.nonNull(sValue)) {
            return sValue;
        }
        // not found, try load value.
        return loadValueByValueLoaderAndCacheIt(key, valueLoader, timeout);
    }

    @SuppressWarnings("Duplicates")
    private <T> T loadValueByValueLoaderAndCacheIt(@Nonnull String key,
                                                   @Nonnull Callable<T> valueLoader,
                                                   long timeout) {
        T loadedValue;
        try {
            loadedValue = valueLoader.call();
        } catch (Exception e) {
            throw new TcException(e, key, valueLoader);
        }
        checkNotNull(loadedValue, "empty loaded value is not allowed");
        TcUtils.doQuietly(() -> {
            redisTemplate.opsForValue().set(key, loadedValue, timeout, TimeUnit.MILLISECONDS);
        });
        return loadedValue;
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean exists(@Nonnull String key) {
        checkNotNull(key, "empty key is not allowed");
        return redisTemplate.execute((RedisCallback<Boolean>) connection -> connection
                .exists(((RedisSerializer<String>) redisTemplate.getKeySerializer()).serialize(key)));
    }

    @Override
    public void expire(@Nonnull String key, long timeout) {
        checkNotNull(key, "empty key is not allowed");
        redisTemplate.expire(key, timeout, TimeUnit.MILLISECONDS);
    }

    @Override
    public void delete(@Nonnull String key) {
        checkNotNull(key, "empty key is not allowed");
        redisTemplate.delete(key);
    }

    @Override
    public String buildLogicKey(@Nonnull String group, @Nonnull String... keys) {
        return group + ":" + Arrays.stream(keys).collect(Collectors.joining(":"));
    }

    @Override
    public String buildHashtag(@Nonnull String hashtag,
                               @Nonnull String key) {
        return "{" + hashtag + "}" + key;
    }

    @Override
    public TcOpsForGroupedValue opsForGroupedValue() {
        return tcOpsForGroupedValue;
    }

}
