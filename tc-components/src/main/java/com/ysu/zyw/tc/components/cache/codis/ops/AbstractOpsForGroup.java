package com.ysu.zyw.tc.components.cache.codis.ops;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Nonnull;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import static com.google.common.base.Preconditions.checkNotNull;

public abstract class AbstractOpsForGroup {

    protected static final String GROUP_NAME_KEY_SPLIT = ":";

    protected static final String GROUP_ZSET_FIELD_PREFIX = "group:";

    @Getter
    @Setter
    protected RedisTemplate<String, Serializable> codisTemplate;

    protected void delete(@Nonnull String group, long min, long max) {
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

    protected String buildGroupedKey(@Nonnull String group, @Nonnull String key) {
        checkNotNull(group, "empty group is not allowed");
        checkNotNull(key, "empty key is not allowed");
        return GROUP_ZSET_FIELD_PREFIX + group + GROUP_NAME_KEY_SPLIT + key;
    }

    protected long buildGroupedZSetFieldScore(long timeout) {
        return new Date(new Date().getTime() + timeout).getTime();
    }

}
