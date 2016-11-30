package com.ysu.zyw.tc.components.cache.redis.ops;

import com.ysu.zyw.tc.components.cache.TcAbstractOpsForGroupedValue;
import com.ysu.zyw.tc.components.cache.TcOpsForGroupedValue;
import com.ysu.zyw.tc.sys.ex.TcException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.serializer.RedisSerializer;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.Serializable;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import static com.google.common.base.Preconditions.checkNotNull;

@Slf4j
public class TcRedisOpsForGroupedValue extends TcAbstractOpsForGroupedValue {

    @Override
    public Set<String> keys(@Nonnull String group) {
        checkNotNull(group, "empty group is not allowed");
        Set<String> keys = redisTemplate.keys(GROUP_FIELD_PREFIX + group + GROUP_NAME_KEY_SPLIT + "*");
        if (log.isDebugEnabled()) {
            log.debug("load [{}] keys in group [{}]", keys.size(), group);
        }
        return keys;
    }

    @Override
    public void delete(@Nonnull String group) {
        checkNotNull(group, "empty group is not allowed");
        Set<String> keys = redisTemplate.keys(GROUP_FIELD_PREFIX + group + GROUP_NAME_KEY_SPLIT + "*");
        redisTemplate.delete(keys);
        if (log.isDebugEnabled()) {
            log.debug("delete keys in group [{}]", keys.size(), group);
        }
    }

}
