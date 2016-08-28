package com.ysu.zyw.tc.components.cache;

import com.google.common.base.Preconditions;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import redis.clients.jedis.Jedis;
import redis.clients.util.Pool;

import java.util.Objects;

@Slf4j
public class CodisConnectionFactory extends JedisConnectionFactory {

    @Getter
    @Setter
    protected CodisPool codisPool;

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
        if (Objects.nonNull(codisPool)) {
            try {
                codisPool.close();
                log.info("success close codis pool ...");
            } catch (Exception ex) {
                log.warn("Cannot properly close codis pool", ex);
            }
            codisPool = null;
        }
    }

    @Override
    protected Pool<Jedis> createRedisPool() {
        codisPool = new CodisPool(
                zkAddr,
                zkSessionTimeoutMs,
                zkProxyDir,
                this.getPoolConfig(),
                this.getTimeout(),
                this.getPassword());
        Preconditions.checkNotNull(codisPool, "codis pool creation failed");
        log.info("success create codis pool ...");
        return codisPool;
    }

}
