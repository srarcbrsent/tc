package com.ysu.zyw.tc.components.cache.codis.ops;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;

import java.util.List;
import java.util.function.Consumer;

import static com.google.common.base.Preconditions.checkNotNull;

@Slf4j
public class TcCodisServerDelegate implements InitializingBean {

    @Getter
    @Setter
    List<JedisConnectionFactory> connectionFactoryList;

    public void doInEveryCodisServer(Consumer<JedisConnectionFactory> worker) {
        checkNotNull(connectionFactoryList);
        connectionFactoryList.forEach(connectionFactory -> {
            try {
                worker.accept(connectionFactory);
            } catch (Exception e) {
                log.error("", e);
            }
        });
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("lazy init codis server delegate ... [{}] factory created ...", connectionFactoryList.size());
    }

}
