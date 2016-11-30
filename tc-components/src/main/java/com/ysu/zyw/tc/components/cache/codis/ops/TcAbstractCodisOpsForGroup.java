package com.ysu.zyw.tc.components.cache.codis.ops;

import com.ysu.zyw.tc.components.cache.TcOpsForGroupedValue;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Nonnull;
import java.util.Date;
import java.util.Set;

import static com.google.common.base.Preconditions.checkNotNull;

public abstract class TcAbstractCodisOpsForGroup implements TcOpsForGroupedValue {

    protected static final String GROUP_NAME_KEY_SPLIT = ":";

    protected static final String GROUP_FIELD_PREFIX = "group:";

    @Getter
    @Setter
    protected long overtime = 5000;

    @Getter
    @Setter
    protected RedisTemplate<String, Object> redisTemplate;

    protected void delete(@Nonnull String group, long min, long max) {
        Set<Object> rangedKeys = redisTemplate.opsForZSet().rangeByScore(group, min, max);
        rangedKeys.parallelStream().forEach(sValue -> {
            String rangedKey = (String) sValue;
            redisTemplate.delete(rangedKey);
            redisTemplate.opsForZSet().remove(group, rangedKey);
        });
        if (redisTemplate.opsForZSet().size(group) == 0) {
            redisTemplate.delete(group);
        }
    }

    protected String buildGroupedKey(@Nonnull String group, @Nonnull String key) {
        checkNotNull(group, "empty group is not allowed");
        checkNotNull(key, "empty key is not allowed");
        return GROUP_FIELD_PREFIX + group + GROUP_NAME_KEY_SPLIT + key;
    }

    protected long buildGroupedZSetFieldScore(long timeout) {
        // this place, all grouped zset score will add an overtime, so that
        // can be sure the zset keys will always more than the real exists
        // grouped keys, but this overtime will influence the keys api.
        return new Date(new Date().getTime() + timeout + overtime).getTime();
    }

}
