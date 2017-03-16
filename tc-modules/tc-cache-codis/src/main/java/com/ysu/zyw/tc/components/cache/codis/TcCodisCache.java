package com.ysu.zyw.tc.components.cache.codis;

import com.ysu.zyw.tc.base.utils.TcFormatUtils;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.cache.Cache;
import org.springframework.cache.support.SimpleValueWrapper;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Nonnull;
import java.io.Serializable;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * TcCodisCache is a impl directly based on codis service
 *
 * @author yaowu.zhang
 */
@Slf4j
public class TcCodisCache implements Cache, InitializingBean {

    @Setter
    private String name = "default";

    @Getter
    @Setter
    private long expiration = -1;

    @Getter
    @Setter
    private boolean pessimistic = false;

    private static final ReentrantLock LOCK_VALUE_LOCK = new ReentrantLock();

    @Getter
    @Setter
    private RedisTemplate<Serializable, Serializable> codisTemplate;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Object getNativeCache() {
        return codisTemplate;
    }

    @Override
    public ValueWrapper get(Object key) {
        checkNotNull(key, "null key is not allowed");
        checkArgument(key instanceof Serializable, "key must implements Serializable");
        Serializable sValue = codisTemplate.opsForValue().get(key);
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

    @SuppressWarnings("unchecked")
    @Override
    public <T> T get(Object key, Class<T> type) {
        checkNotNull(key, "null key is not allowed");
        checkArgument(key instanceof Serializable, "key must implements Serializable");
        T value = (T) codisTemplate.opsForValue().get(key);
        if (log.isDebugEnabled()) {
            log.debug("get object [{}] from cache by key [{}]", value, key);
        }
        return value;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T get(Object key, Callable<T> valueLoader) {
        checkNotNull(key, "null key is not allowed");
        checkNotNull(valueLoader, "null value loader is not allowed");
        T value;
        try {
            value = (T) codisTemplate.opsForValue().get(key);
        } catch (Exception e) {
            log.error("", e);
            return loadValue(key, valueLoader, expiration);
        }
        if (Objects.nonNull(value)) {
            if (log.isDebugEnabled()) {
                log.debug("get object [{}] from cache by key [{}]", value, key);
            }
            return value;
        } else {
            if (pessimistic) {
                try {
                    LOCK_VALUE_LOCK.lock();
                    return tryLoadValue(key, valueLoader);
                } finally {
                    LOCK_VALUE_LOCK.unlock();
                }
            } else {
                return tryLoadValue(key, valueLoader);
            }
        }
    }

    @SuppressWarnings("unchecked")
    private <T> T tryLoadValue(Object key, Callable<T> valueLoader) {
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
        // not found, try load value
        return loadValue(key, valueLoader, expiration);
    }


    private <T> T loadValue(@Nonnull Object key, @Nonnull Callable<T> valueLoader, long timeout) {
        T loadedValue;
        try {
            loadedValue = valueLoader.call();
        } catch (Exception e) {
            throw new IllegalStateException(TcFormatUtils.format("key [{}] load value failed", key), e);
        }
        checkNotNull(loadedValue, "empty loaded value is not allowed");
        try {
            codisTemplate.opsForValue().set((Serializable) key, (Serializable) loadedValue, timeout,
                    TimeUnit.MILLISECONDS);
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
    public void put(Object key, Object value) {
        checkNotNull(key, "null key is not allowed");
        checkArgument(key instanceof Serializable, "key must implements Serializable");
        checkNotNull(value, "null value is not allowed");
        checkArgument(value instanceof Serializable, "value must implements Serializable");
        codisTemplate.opsForValue().set((Serializable) key, (Serializable) value, expiration, TimeUnit.MILLISECONDS);
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
            return null;
        } else {
            return new SimpleValueWrapper(existValue);
        }
    }

    @Override
    public void evict(Object key) {
        checkNotNull(key, "null key is not allowed");
        checkArgument(key instanceof Serializable, "key must implements Serializable");
        codisTemplate.delete((Serializable) key);
        if (log.isDebugEnabled()) {
            log.debug("evict cache by key [{}]", key);
        }
    }

    @Override
    public void clear() {
        throw new IllegalStateException("clear is a not supported operation");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (expiration <= 0) {
            log.warn("the default expiration is set to [{}], and it's a un expire type", expiration);
        }
    }

}
