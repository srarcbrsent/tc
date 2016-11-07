package com.ysu.zyw.tc.components.cache.redis.ops;

import com.ysu.zyw.tc.components.cache.TcOpsForGroupedValue;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Nonnull;

import static com.google.common.base.Preconditions.checkNotNull;

public abstract class TcAbstractRedisOpsForGroup implements TcOpsForGroupedValue {

    protected static final String GROUP_NAME_KEY_SPLIT = ":";

    protected static final String GROUP_FIELD_PREFIX = "group:";

    @Getter
    @Setter
    protected RedisTemplate<String, Object> redisTemplate;

    protected String buildGroupedKey(@Nonnull String group, @Nonnull String key) {
        checkNotNull(group, "empty group is not allowed");
        checkNotNull(key, "empty key is not allowed");
        return GROUP_FIELD_PREFIX + group + GROUP_NAME_KEY_SPLIT + key;
    }

}
