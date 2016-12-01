package com.ysu.zyw.tc.components.cache.codis.ops;

import com.ysu.zyw.tc.components.cache.TcAbstractOpsForGroupedValue;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.serializer.RedisSerializer;

import javax.annotation.Nonnull;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

@Slf4j
public class TcCodisOpsForGroupedValue extends TcAbstractOpsForGroupedValue implements ApplicationContextAware {

    // this is only for lazy init tc codis server delegate, only the first call of keys or delete
    // will lead to init tc codis server delegate, or sometimes this two api is useless, then
    // the codis server delegate will never be init.
    private ApplicationContext applicationContext;

    @SuppressWarnings("unchecked")
    @Override
    public Set<String> keys(@Nonnull String group) {
        checkNotNull(group, "empty group is not allowed");
        // try merge all the master codis-server keys.
        RedisSerializer<String> keySerializer = (RedisSerializer<String>) redisTemplate.getKeySerializer();
        TcCodisServerDelegate tcCodisServerDelegate = applicationContext.getBean(TcCodisServerDelegate.class);
        Set<String> keys = new HashSet<>();
        tcCodisServerDelegate.doInEveryCodisServer(connectionFactory -> {
            byte[] sKey = keySerializer.serialize(GROUP_FIELD_PREFIX + group + GROUP_NAME_KEY_SPLIT + "*");
            RedisConnection connection = connectionFactory.getConnection();
            Set<byte[]> sValue = connection.keys(sKey);
            int bSize = keys.size();
            keys.addAll(sValue.stream().map(keySerializer::deserialize).collect(Collectors.toSet()));
            log.info("from codis server [{}:{}] all load [{}] keys, current keys [{}]",
                    connectionFactory.getHostName(), connectionFactory.getPort(), keys.size() - bSize, keys.size());
        });
        return keys;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void delete(@Nonnull String group) {
        checkArgument(StringUtils.isNotBlank(group), "empty group is not allowed");
        // try delete all the master codis-server keys.
        RedisSerializer<String> keySerializer = (RedisSerializer<String>) redisTemplate.getKeySerializer();
        TcCodisServerDelegate tcCodisServerDelegate = applicationContext.getBean(TcCodisServerDelegate.class);
        tcCodisServerDelegate.doInEveryCodisServer(connectionFactory -> {
            byte[] sKey = keySerializer.serialize(GROUP_FIELD_PREFIX + group + GROUP_NAME_KEY_SPLIT + "*");
            RedisConnection connection = connectionFactory.getConnection();
            Set<byte[]> sValue = connection.keys(sKey);
            Long delC = connection.del(sValue.toArray(new byte[sValue.size()][]));
            log.info("from codis server [{}:{}] all del [{}] keys",
                    connectionFactory.getHostName(), connectionFactory.getPort(), delC);
        });
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

}
