package com.ysu.zyw.tc.components.cache.codis;

import com.ysu.zyw.tc.components.cache.codis.ops.TcOpsForGroupedValue;
import com.ysu.zyw.tc.sys.ex.TcException;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

import javax.annotation.Nonnull;
import javax.annotation.Resource;
import java.io.Serializable;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * TcCodisService provide codis upper-level operations
 *
 * @author yaowu.zhang
 */
@Slf4j
public class TcCodisService {

    @Getter
    @Setter
    private RedisTemplate<String, Serializable> codisTemplate;

    @Resource
    private TcOpsForGroupedValue tcOpsForGroupedValue;

    public void set(@Nonnull String key, @Nonnull Serializable value, long timeout) {
        checkNotNull(key, "empty key is not allowed");
        checkNotNull(value, "null value is not allowed");
        codisTemplate.opsForValue().set(key, value, timeout, TimeUnit.MILLISECONDS);
        if (log.isDebugEnabled()) {
            log.debug("cache set key [{}], value [{}], timeout [{}]", key, value, timeout);
        }
    }

    @SuppressWarnings("unchecked")
    public <T> T get(@Nonnull String key, @Nonnull Class<T> clazz) {
        checkNotNull(key, "empty key is not allowed");
        checkNotNull(clazz, "null clazz is not allowed");
        Serializable sValue = codisTemplate.opsForValue().get(key);
        if (log.isDebugEnabled()) {
            log.debug("cache get key [{}], value [{}], clazz [{}]", key, sValue, clazz);
        }
        return (T) sValue;
    }

    @SuppressWarnings("unchecked")
    public <T> T get(@Nonnull String key, @Nonnull Callable<T> valueLoader, long timeout) {
        // special, other apiimpl if the cache service itself is offline, they may throw an exception(such
        // as JodisPool is empty), but this apiimpl is different, because this apiimpl means load by cache, if
        // not loaded, then load by value loader, this not loaded include the cache is not exists and
        // also the cache service itself is offline. so it will catch the other exception.
        checkNotNull(key, "null key is not allowed");
        checkNotNull(valueLoader, "null value loader is not allowed");
        T value;
        try {
            value = (T) codisTemplate.opsForValue().get(key);
        } catch (Exception e) {
            log.error("", e);
            return loadValue(key, valueLoader, timeout);
        }
        if (Objects.nonNull(value)) {
            if (log.isDebugEnabled()) {
                log.debug("get object [{}] from cache by key [{}]", value, key);
            }
            return value;
        } else {
            // FIXME if the key is a dynamic key(the key has so many enumerated values), this place may lead
            // to constant pool memory leak, this place we suppose the key is not inconstancy
            synchronized (key.intern()) {
                // lock and get
                T sValue = null;
                try {
                    sValue = (T) codisTemplate.opsForValue().get(key);
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
                return loadValue(key, valueLoader, timeout);
            }
        }
    }

    private <T> T loadValue(@Nonnull String key, @Nonnull Callable<T> valueLoader, long timeout) {
        T loadedValue;
        try {
            loadedValue = valueLoader.call();
        } catch (Exception e) {
            throw new TcException(e, key, valueLoader);
        }
        checkNotNull(loadedValue, "empty loaded value is not allowed");
        try {
            codisTemplate.opsForValue().set(key, (Serializable) loadedValue, timeout, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            // if cache failed, let the load value succ.
            log.error("", e);
        }
        if (log.isDebugEnabled()) {
            log.debug("put object [{}] into cache by key [{}], timeout [{}]", loadedValue, key, timeout);
        }
        return loadedValue;
    }

    @SuppressWarnings("unchecked")
    public boolean exists(@Nonnull String key) {
        checkNotNull(key, "empty key is not allowed");
        return codisTemplate.execute((RedisCallback<Boolean>) connection -> connection
                .exists(((RedisSerializer<String>) codisTemplate.getKeySerializer()).serialize(key)));
    }

    public void expire(@Nonnull String key, long timeout) {
        checkNotNull(key, "empty key is not allowed");
        codisTemplate.expire(key, timeout, TimeUnit.MILLISECONDS);
    }

    public void delete(@Nonnull String key) {
        checkNotNull(key, "empty key is not allowed");
        codisTemplate.delete(key);
        if (log.isDebugEnabled()) {
            log.debug("cache delete key [{}]", key);
        }
    }

    public String buildHashtagedKey(@Nonnull String hashtag, @Nonnull String key) {
        return "{" + hashtag + "}" + key;
    }

    public TcOpsForGroupedValue opsForGroupedValue() {
        return tcOpsForGroupedValue;
    }

}