package com.ysu.zyw.tc.components.cache.codis.ops;

import com.ysu.zyw.tc.base.ex.TcException;
import com.ysu.zyw.tc.base.utils.TcUtils;
import com.ysu.zyw.tc.components.cache.TcOpsForGroupedValue;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Since codis not support keys command, so the group operation must be redesigned, and we decided to
 * use a hash structure to implement that feature.
 *
 * According to the redis community, if we want to set a expire time to a hash field, we can use that:
 *
 * If you need expiration for "data is invalid after X seconds" reasons, you can store another field
 * in your hash with [fieldname]_expiresAt then always retrieve that with your [fieldname] to check
 * if the data is still valid.
 *
 * @link https://github.com/antirez/redis/issues/1042
 * @warn this is not accurate, it depends on the cluster time.
 */
@Slf4j
public class TcCodisOpsForGroupedValue implements TcOpsForGroupedValue {

    @Getter
    @Setter
    private static long tryLockTimeout = 3000;

    private static final String _EXPIRES_AT = "___expiresAt";

    @Getter
    @Setter
    protected RedisTemplate<String, Serializable> redisTemplate;

    protected String expiresAtFiled(String field) {
        checkNotNull(field);
        if (field.contains(_EXPIRES_AT)) {
            throw new TcException(_EXPIRES_AT + " is a inner access field, you can not use it in your code");
        }
        return field + _EXPIRES_AT;
    }

    protected long expiresAt(long timeout) {
        checkArgument(timeout > 0);
        long expiresAt = new Date().getTime() + timeout;
        if (expiresAt < 0) {
            // overflow
            expiresAt = Long.MAX_VALUE;
        }
        return expiresAt;
    }

    @Override
    public void set(@Nonnull String group,
                    @Nonnull String key,
                    @Nonnull Serializable value,
                    long timeout) {
        checkNotNull(group, "empty group is not allowed");
        checkNotNull(key, "empty key is not allowed");
        checkNotNull(value, "null value is not allowed");
        redisTemplate.opsForHash().put(group, expiresAtFiled(key), expiresAt(timeout));
        redisTemplate.opsForHash().put(group, key, value);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends Serializable> T get(@Nonnull String group,
                                          @Nonnull String key,
                                          @Nonnull Class<T> clazz) {
        checkNotNull(group, "empty group is not allowed");
        checkNotNull(key, "empty key is not allowed");
        checkNotNull(clazz, "null clazz is not allowed");
        if (exists(group, key)) {
            return (T) redisTemplate.opsForHash().get(group, key);
        } else {
            return null;
        }
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
    public <T extends Serializable> T get(@Nonnull String group,
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
        T value = null;
        try {
            if (exists(group, key)) {
                value = (T) redisTemplate.opsForHash().get(group, key);
            }
        } catch (Exception e) {
            log.error("", e);
            return loadValueByValueLoaderAndCacheIt(group, key, valueLoader, timeout);
        }
        if (Objects.nonNull(value)) {
            return value;
        } else {
            if (Objects.isNull(lock)) {
                return loadValueByValueLoaderAndCacheIt(group, key, valueLoader, timeout);
            } else {
                try {
                    lock.tryLock(tryLockTimeout, TimeUnit.MILLISECONDS);
                    return loadValue(group, key, valueLoader, timeout);
                } catch (InterruptedException e) {
                    throw new TcException("load cache blocked, throw exception");
                } finally {
                    lock.unlock();
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    private <T extends Serializable> T loadValue(@Nonnull String group,
                                                 @Nonnull String key,
                                                 @Nonnull Callable<T> valueLoader,
                                                 long timeout) {
        // lock and get
        T sValue = TcUtils.doQuietly(() -> {
            if (exists(group, key)) {
                return (T) redisTemplate.opsForHash().get(group, key);
            } else {
                return null;
            }
        }, null);
        if (Objects.nonNull(sValue)) {
            return sValue;
        }
        // not found, try load value.
        return loadValueByValueLoaderAndCacheIt(group, key, valueLoader, timeout);
    }

    @SuppressWarnings({"Duplicates", "UnusedParameters"})
    private <T extends Serializable> T loadValueByValueLoaderAndCacheIt(@Nonnull String group,
                                                                        @Nonnull String key,
                                                                        @Nonnull Callable<T> valueLoader,
                                                                        long timeout) {
        T loadedValue;
        try {
            loadedValue = valueLoader.call();
        } catch (Exception e) {
            throw new TcException(e, valueLoader);
        }
        checkNotNull(loadedValue, "empty loaded value is not allowed");
        TcUtils.doQuietly(() -> set(group, key, loadedValue, timeout));
        return loadedValue;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean exists(@Nonnull String group,
                          @Nonnull String key) {
        checkNotNull(group, "empty group is not allowed");
        checkNotNull(key, "empty key is not allowed");
        Long expiresAt = (Long) redisTemplate.opsForHash().get(group, expiresAtFiled(key));
        if (new Date().getTime() > expiresAt) {
            // expire
            delete(group, key);
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void expire(@Nonnull String group,
                       @Nonnull String key,
                       long timeout) {
        checkNotNull(group, "empty group is not allowed");
        checkNotNull(key, "empty key is not allowed");
        if (exists(group, key)) {
            redisTemplate.opsForHash().put(group, expiresAtFiled(key), expiresAt(timeout));
        }
    }

    @Override
    public void delete(@Nonnull String group,
                       @Nonnull String key) {
        checkNotNull(group, "empty group is not allowed");
        checkNotNull(key, "empty key is not allowed");
        redisTemplate.opsForHash().delete(group, key, expiresAtFiled(key));
    }

    // @warn may contains some expired keys, no necessary to implement accurate keys command.
    @Override
    public Set<String> keys(@Nonnull String group) {
        checkNotNull(group, "empty group is not allowed");
        Set<Object> oKeys = redisTemplate.opsForHash().keys(group);
        return oKeys.stream()
                .map(Object::toString)
                .filter(key -> !key.contains(_EXPIRES_AT))
                .collect(Collectors.toSet());
    }

    @Override
    public void delete(@Nonnull String group) {
        checkNotNull(group, "empty group is not allowed");
        redisTemplate.delete(group);
    }

}
