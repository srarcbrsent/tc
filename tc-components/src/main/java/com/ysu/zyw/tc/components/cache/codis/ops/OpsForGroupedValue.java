package com.ysu.zyw.tc.components.cache.codis.ops;

import lombok.Getter;
import lombok.Setter;
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

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author yaowu.zhang
 * @warn this group api is highly depends on the time in distribution system jvm.
 * because the ops for value use the relative time as timeout, but the zset use the
 * absolute time to count the relation of values, so if the time in the cluster is
 * scattered, then the zset score will not match the real timeout, and the api
 * keys will be not perfect match, but the other api is use the relative time so
 * do not influenced.
 */
public class OpsForGroupedValue {

    private static final String GROUP_ZSET_FIELD_PREFIX = "group:";

    private static final String GROUP_NAME_KEY_SPLIT = ":";

    @Getter
    @Setter
    private RedisTemplate<String, Serializable> codisTemplate;

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
    }

    public <T> T get(@Nonnull String group,
                     @Nonnull String key,
                     @Nonnull Class<T> clazz) {
        checkNotNull(group, "empty group is not allowed");
        checkNotNull(key, "empty key is not allowed");
        checkNotNull(clazz, "null clazz is not allowed");
        String groupedKey = buildGroupedKey(group, key);
        Serializable sValue = codisTemplate.opsForValue().get(groupedKey);
        if (Objects.isNull(sValue)) {
            codisTemplate.opsForZSet().remove(group, groupedKey);
            if (codisTemplate.opsForZSet().size(group) == 0) {
                codisTemplate.delete(group);
            }
        }
        //noinspection unchecked
        return (T) sValue;
    }

    public boolean exists(@Nonnull String group, @Nonnull String key) {
        checkNotNull(group, "empty group is not allowed");
        checkNotNull(key, "empty key is not allowed");
        //noinspection unchecked
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

    public String buildGroupedKey(@Nonnull String group, @Nonnull String key) {
        checkNotNull(group, "empty group is not allowed");
        checkNotNull(key, "empty key is not allowed");
        return GROUP_ZSET_FIELD_PREFIX + group + GROUP_NAME_KEY_SPLIT + key;
    }

    private long buildGroupedZSetFieldScore(long timeout) {
        return new Date(new Date().getTime() + timeout).getTime();
    }

}
