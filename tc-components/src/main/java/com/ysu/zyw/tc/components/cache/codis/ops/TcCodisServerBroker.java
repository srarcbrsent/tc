package com.ysu.zyw.tc.components.cache.codis.ops;

import lombok.*;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;

import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

import static com.google.common.base.Preconditions.checkNotNull;

@Slf4j
public class TcCodisServerBroker {

    @Getter
    @Setter
    List<TcJedisConfig> tcJedisConfigList;

    // package visible
    void doInEveryCodisServer(Consumer<Jedis> worker) {
        checkNotNull(tcJedisConfigList);
        log.info("going to call do in every codis server in [{}] server", tcJedisConfigList.size());
        tcJedisConfigList.forEach(tcJedisConfig -> {
            try (Jedis jedis = new Jedis(tcJedisConfig.getHost(), tcJedisConfig.getPort())) {
                if (Objects.nonNull(tcJedisConfig.getPassword())) {
                    jedis.auth(tcJedisConfig.getPassword());
                }
                log.info("create jedis instance of [{}:{}]", tcJedisConfig.getHost(), tcJedisConfig.getPort());
                worker.accept(jedis);
            } catch (Exception e) {
                log.error("", e);
            }
        });
    }

    @Data
    @Accessors(chain = true)
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TcJedisConfig {

        private String host;

        private int port;

        private String password;

    }

}
