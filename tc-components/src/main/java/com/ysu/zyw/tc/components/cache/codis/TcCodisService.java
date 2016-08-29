package com.ysu.zyw.tc.components.cache.codis;

import com.google.common.base.Preconditions;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

import javax.annotation.Nonnull;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

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

    @Getter
    private OpsForGroupedValue opsForGroupedValue = new OpsForGroupedValue();

    private static final String GROUP_ZSET_FIELD_PREFIX = "group:";

    private static final String GROUP_NAME_KEY_SPLIT = ":";

    public void set(@Nonnull String key, @Nonnull Serializable value, long timeout) {
        Preconditions.checkNotNull(key, "empty key is not allowed");
        Preconditions.checkNotNull(value, "null value is not allowed");
        codisTemplate.opsForValue().set(key, value, timeout, TimeUnit.MILLISECONDS);
        if (log.isDebugEnabled()) {
            log.debug("cache set key [{}], value [{}], timeout [{}]", key, value, timeout);
        }
    }

    @SuppressWarnings("unchecked")
    public <T> T get(@Nonnull String key, @Nonnull Class<T> clazz) {
        Preconditions.checkNotNull(key, "empty key is not allowed");
        Preconditions.checkNotNull(clazz, "null clazz is not allowed");
        Serializable sValue = codisTemplate.opsForValue().get(key);
        if (log.isDebugEnabled()) {
            log.debug("cache get key [{}], value [{}], clazz [{}]", key, sValue, clazz);
        }
        return (T) sValue;
    }

    @SuppressWarnings("unchecked")
    public boolean exists(@Nonnull String key) {
        Preconditions.checkNotNull(key, "empty key is not allowed");
        return codisTemplate.execute((RedisCallback<Boolean>) connection -> connection
                .exists(((RedisSerializer<String>) codisTemplate.getKeySerializer()).serialize(key)));
    }

    public void expire(@Nonnull String key, long timeout) {
        Preconditions.checkNotNull(key, "empty key is not allowed");
        codisTemplate.expire(key, timeout, TimeUnit.MILLISECONDS);
    }

    public void delete(@Nonnull String key) {
        Preconditions.checkNotNull(key, "empty key is not allowed");
        codisTemplate.delete(key);
        if (log.isDebugEnabled()) {
            log.debug("cache delete key [{}]", key);
        }
    }

    public class OpsForGroupedValue {

        public void set(@Nonnull String group, @Nonnull String key, @Nonnull Serializable value, long timeout) {
            Preconditions.checkNotNull(group, "empty group is not allowed");
            Preconditions.checkNotNull(key, "empty key is not allowed");
            Preconditions.checkNotNull(value, "null value is not allowed");
            String groupedKey = buildGroupedKey(group, key);
            codisTemplate.opsForValue().set(groupedKey, value, timeout, TimeUnit.MILLISECONDS);
            codisTemplate.opsForZSet().add(group, groupedKey, buildGroupedZSetFieldScore(timeout));
        }

        @SuppressWarnings("unchecked")
        public <T> T get(@Nonnull String group, @Nonnull String key, @Nonnull Class<T> clazz) {
            Preconditions.checkNotNull(group, "empty group is not allowed");
            Preconditions.checkNotNull(key, "empty key is not allowed");
            Preconditions.checkNotNull(clazz, "null clazz is not allowed");
            String groupedKey = buildGroupedKey(group, key);
            Serializable sValue = codisTemplate.opsForValue().get(groupedKey);
            if (Objects.isNull(sValue)) {
                codisTemplate.opsForZSet().remove(group, groupedKey);
                if (codisTemplate.opsForZSet().size(group) == 0) {
                    codisTemplate.delete(group);
                }
            }
            return (T) sValue;
        }

        @SuppressWarnings("unchecked")
        public boolean exists(@Nonnull String group, @Nonnull String key) {
            Preconditions.checkNotNull(group, "empty group is not allowed");
            Preconditions.checkNotNull(key, "empty key is not allowed");
            return codisTemplate.execute((RedisCallback<Boolean>) connection -> connection
                    .exists(((RedisSerializer<String>) codisTemplate.getKeySerializer())
                            .serialize(buildGroupedKey(group, key))));
        }

        public void delete(@Nonnull String group, @Nonnull String key) {
            Preconditions.checkNotNull(group, "empty group is not allowed");
            Preconditions.checkNotNull(key, "empty key is not allowed");
            String groupedKey = buildGroupedKey(group, key);
            codisTemplate.delete(groupedKey);
            codisTemplate.opsForZSet().remove(group, groupedKey);
            if (codisTemplate.opsForZSet().size(group) == 0) {
                codisTemplate.delete(group);
            }
        }

        public Set<String> keys(@Nonnull String group) {
            Preconditions.checkNotNull(group, "empty group is not allowed");
            long now = new Date().getTime();
            delete(group, 0, now);
            Set<Serializable> sValidKeys = codisTemplate.opsForZSet().rangeByScore(group, now, Long.MAX_VALUE);
            return sValidKeys.parallelStream().map(sValidKey -> (String) sValidKey)
                    .map(validKey -> validKey.split(GROUP_NAME_KEY_SPLIT)[2]).collect(Collectors.toSet());
        }

        public void delete(@Nonnull String group) {
            Preconditions.checkNotNull(group, "empty group is not allowed");
            delete(group, 0, Long.MAX_VALUE);
        }

        private void delete(@Nonnull String group, long min, long max) {
            Set<Serializable> expiredKeys = codisTemplate.opsForZSet().rangeByScore(group, min, max);
            expiredKeys.parallelStream().forEach(sValue -> {
                String expiredKey = (String) sValue;
                codisTemplate.delete(expiredKey);
                codisTemplate.opsForZSet().remove(group, expiredKey);
            });
            if (codisTemplate.opsForZSet().size(group) == 0) {
                codisTemplate.delete(group);
            }
        }

    }

    public String buildGroupedKey(@Nonnull String group, @Nonnull String key) {
        Preconditions.checkNotNull(group, "empty group is not allowed");
        Preconditions.checkNotNull(key, "empty key is not allowed");
        return GROUP_ZSET_FIELD_PREFIX + group + GROUP_NAME_KEY_SPLIT + key;
    }

    public String buildHashtagedKey(@Nonnull String hashtag, @Nonnull String key) {
        return "{" + hashtag + "}" + key;
    }

    private long buildGroupedZSetFieldScore(long timeout) {
        return new Date(new Date().getTime() + timeout).getTime();
    }

}