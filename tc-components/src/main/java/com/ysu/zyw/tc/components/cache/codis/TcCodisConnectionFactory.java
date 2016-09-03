package com.ysu.zyw.tc.components.cache.codis;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import redis.clients.jedis.Jedis;
import redis.clients.util.Pool;

import java.util.Objects;

import static com.google.common.base.Preconditions.checkNotNull;

@Slf4j
public class TcCodisConnectionFactory extends JedisConnectionFactory {

    @Getter
    @Setter
    protected TcCodisPool tcCodisPool;

    @Getter
    @Setter
    protected String zkAddr;

    @Getter
    @Setter
    protected int zkSessionTimeoutMs;

    @Getter
    @Setter
    protected String zkProxyDir;

    @Override
    public void destroy() {
        super.destroy();
        if (Objects.nonNull(tcCodisPool)) {
            try {
                tcCodisPool.close();
                log.info("success close codis pool ...");
            } catch (Exception ex) {
                log.warn("Cannot properly close codis pool", ex);
            }
            tcCodisPool = null;
        }
    }

    @Override
    protected Pool<Jedis> createRedisPool() {
        tcCodisPool = new TcCodisPool(
                zkAddr,
                zkSessionTimeoutMs,
                zkProxyDir,
                this.getPoolConfig(),
                this.getTimeout(),
                this.getPassword());
        checkNotNull(tcCodisPool, "codis pool creation failed");
        log.info("success create codis pool ...");
        return tcCodisPool;
    }

}
