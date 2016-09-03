package com.ysu.zyw.tc.components.cache.codis;

import com.ysu.zyw.tc.sys.ex.TcException;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.support.SimpleValueWrapper;
import org.springframework.data.redis.core.RedisTemplate;

import java.io.Serializable;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * TcCodisCache is a impl directly based on codis service
 *
 * @author yaowu.zhang
 */
@Slf4j
public class TcCodisCache implements Cache {

    @Setter
    private String name = "default";

    @Getter
    @Setter
    private long expiration = -1;

    @Getter
    @Setter
    private RedisTemplate<Serializable, Serializable> redisTemplate;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Object getNativeCache() {
        return redisTemplate;
    }

    @Override
    public ValueWrapper get(Object key) {
        checkNotNull(key, "null key is not allowed");
        checkArgument(key instanceof Serializable, "key must implements Serializable");
        Serializable sValue = redisTemplate.opsForValue().get(key);
        if (Objects.nonNull(sValue)) {
            if (log.isDebugEnabled()) {
                log.debug("get object [{}] from cache by key [{}]", sValue, key);
            }
            return new SimpleValueWrapper(sValue);
        } else {
            if (log.isDebugEnabled()) {
                log.debug("get object [{}] from cache by key [{}]", null, key);
            }
            return null;
        }
    }

    @Override
    public <T> T get(Object key, Class<T> type) {
        checkNotNull(key, "null key is not allowed");
        checkArgument(key instanceof Serializable, "key must implements Serializable");
        //noinspection unchecked
        T value = (T) redisTemplate.opsForValue().get(key);
        if (log.isDebugEnabled()) {
            log.debug("get object [{}] from cache by key [{}]", value, key);
        }
        return value;
    }

    @Override
    public <T> T get(Object key, Callable<T> valueLoader) {
        //noinspection unchecked
        T value = (T) redisTemplate.opsForValue().get(key);
        if (Objects.nonNull(value)) {
            if (log.isDebugEnabled()) {
                log.debug("get object [{}] from cache by key [{}]", value, key);
            }
            return value;
        } else {
            try {
                T loadedValue = valueLoader.call();
                redisTemplate.opsForValue().set((Serializable) key, (Serializable) loadedValue, expiration,
                        TimeUnit.MILLISECONDS);
                if (log.isDebugEnabled()) {
                    log.debug("put object [{}] into cache by key [{}], expiration [{}]", loadedValue, key, expiration);
                }
                return loadedValue;
            } catch (Exception e) {
                throw new ValueRetrievalException(key, valueLoader, e);
            }
        }
    }

    @Override
    public void put(Object key, Object value) {
        checkNotNull(key, "null key is not allowed");
        checkArgument(key instanceof Serializable, "key must implements Serializable");
        checkNotNull(value, "null value is not allowed");
        checkArgument(value instanceof Serializable, "value must implements Serializable");
        redisTemplate.opsForValue().set((Serializable) key, (Serializable) value, expiration, TimeUnit.MILLISECONDS);
        if (log.isDebugEnabled()) {
            log.debug("put object [{}] into cache by key [{}], expiration [{}]", value, key, expiration);
        }
    }

    @Override
    public ValueWrapper putIfAbsent(Object key, Object value) {
        checkNotNull(key, "null key is not allowed");
        checkArgument(key instanceof Serializable, "key must implements Serializable");
        checkNotNull(value, "null value is not allowed");
        checkArgument(value instanceof Serializable, "value must implements Serializable");
        Serializable existValue = get(key, Serializable.class);
        if (Objects.isNull(existValue)) {
            put(key, value);
            return new SimpleValueWrapper(value);
        } else {
            return new SimpleValueWrapper(existValue);
        }
    }

    @Override
    public void evict(Object key) {
        checkNotNull(key, "null key is not allowed");
        checkArgument(key instanceof Serializable, "key must implements Serializable");
        redisTemplate.delete((Serializable) key);
        if (log.isDebugEnabled()) {
            log.debug("evict cache by key [{}]", key);
        }
    }

    @Override
    public void clear() {
        throw new TcException("clear is a not supported operation");
    }

}