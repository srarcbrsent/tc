package com.ysu.zyw.tc.components.cache.codis;

import com.ysu.zyw.tc.components.cache.codis.ops.OpsForGroupedValue;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

import javax.annotation.Nonnull;
import javax.annotation.Resource;
import java.io.Serializable;
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
    private OpsForGroupedValue opsForGroupedValue;

    public void set(@Nonnull String key, @Nonnull Serializable value, long timeout) {
        checkNotNull(key, "empty key is not allowed");
        checkNotNull(value, "null value is not allowed");
        codisTemplate.opsForValue().set(key, value, timeout, TimeUnit.MILLISECONDS);
        if (log.isDebugEnabled()) {
            log.debug("cache set key [{}], value [{}], timeout [{}]", key, value, timeout);
        }
    }

    public <T> T get(@Nonnull String key, @Nonnull Class<T> clazz) {
        checkNotNull(key, "empty key is not allowed");
        checkNotNull(clazz, "null clazz is not allowed");
        Serializable sValue = codisTemplate.opsForValue().get(key);
        if (log.isDebugEnabled()) {
            log.debug("cache get key [{}], value [{}], clazz [{}]", key, sValue, clazz);
        }
        //noinspection unchecked
        return (T) sValue;
    }

    public boolean exists(@Nonnull String key) {
        checkNotNull(key, "empty key is not allowed");
        //noinspection unchecked
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

    public OpsForGroupedValue opsForGroupedValue() {
        return opsForGroupedValue;
    }

}