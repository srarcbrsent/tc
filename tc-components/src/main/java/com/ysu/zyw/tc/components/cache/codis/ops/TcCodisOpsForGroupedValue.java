package com.ysu.zyw.tc.components.cache.codis.ops;

import com.ysu.zyw.tc.components.cache.TcAbstractOpsForGroupedValue;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.serializer.RedisSerializer;

import javax.annotation.Nonnull;
import javax.annotation.Resource;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

@Slf4j
public class TcCodisOpsForGroupedValue extends TcAbstractOpsForGroupedValue {

    @Resource
    private TcCodisServerBroker tcCodisServerBroker;

    @SuppressWarnings("unchecked")
    @Override
    public Set<String> keys(@Nonnull String group) {
        checkNotNull(group, "empty group is not allowed");
        // try merge all the master codis-server keys.
        RedisSerializer<String> keySerializer = (RedisSerializer<String>) redisTemplate.getKeySerializer();
        Set<String> keys = new HashSet<>();
        tcCodisServerBroker.doInEveryCodisServer(jedis -> {
            byte[] sKey = keySerializer.serialize(GROUP_FIELD_PREFIX + group + GROUP_NAME_KEY_SPLIT + "*");
            Set<byte[]> sValue = jedis.keys(sKey);
            int bSize = keys.size();
            keys.addAll(sValue.stream().map(keySerializer::deserialize).collect(Collectors.toSet()));
            log.info("from codis server [{}:{}] all load [{}] keys, current keys [{}]",
                    jedis.getClient().getHost(), jedis.getClient().getPort(), keys.size() - bSize, keys.size());
        });
        return keys;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void delete(@Nonnull String group) {
        checkArgument(StringUtils.isNotBlank(group), "empty group is not allowed");
        // try delete all the master codis-server keys.
        RedisSerializer<String> keySerializer = (RedisSerializer<String>) redisTemplate.getKeySerializer();
        tcCodisServerBroker.doInEveryCodisServer(jedis -> {
            byte[] sKey = keySerializer.serialize(GROUP_FIELD_PREFIX + group + GROUP_NAME_KEY_SPLIT + "*");
            Set<byte[]> sValue = jedis.keys(sKey);
            long delC = jedis.del(sValue.toArray(new byte[sValue.size()][]));
            log.info("from codis server [{}:{}] all del [{}] keys",
                    jedis.getClient().getHost(), jedis.getClient().getPort(), delC);
        });
    }

}
