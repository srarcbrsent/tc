package com.ysu.zyw.tc.components.cache;

import com.google.common.base.Preconditions;
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

/**
 * CodisCache is a impl directly based on codis service
 *
 * @author yaowu.zhang
 */
@Slf4j
public class CodisCache implements Cache {

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
        Preconditions.checkNotNull(key, "null key is not allowed");
        Preconditions.checkArgument(key instanceof Serializable, "key must implements Serializable");
        SimpleValueWrapper simpleValueWrapper = new SimpleValueWrapper(redisTemplate.opsForValue().get(key));
        if (log.isDebugEnabled()) {
            log.debug("get object [{}] from cache by key [{}]", simpleValueWrapper, key);
        }
        return simpleValueWrapper;
    }

    @Override
    public <T> T get(Object key, Class<T> type) {
        Preconditions.checkNotNull(key, "null key is not allowed");
        Preconditions.checkArgument(key instanceof Serializable, "key must implements Serializable");
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
                        TimeUnit.SECONDS);
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
        Preconditions.checkNotNull(key, "null key is not allowed");
        Preconditions.checkArgument(key instanceof Serializable, "key must implements Serializable");
        Preconditions.checkNotNull(value, "null value is not allowed");
        Preconditions.checkArgument(value instanceof Serializable, "value must implements Serializable");
        redisTemplate.opsForValue().set((Serializable) key, (Serializable) value, expiration, TimeUnit.SECONDS);
        if (log.isDebugEnabled()) {
            log.debug("put object [{}] into cache by key [{}], expiration [{}]", value, key, expiration);
        }
    }

    @Override
    public ValueWrapper putIfAbsent(Object key, Object value) {
        Preconditions.checkNotNull(key, "null key is not allowed");
        Preconditions.checkArgument(key instanceof Serializable, "key must implements Serializable");
        Preconditions.checkNotNull(value, "null value is not allowed");
        Preconditions.checkArgument(value instanceof Serializable, "value must implements Serializable");
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
        Preconditions.checkNotNull(key, "null key is not allowed");
        Preconditions.checkArgument(key instanceof Serializable, "key must implements Serializable");
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