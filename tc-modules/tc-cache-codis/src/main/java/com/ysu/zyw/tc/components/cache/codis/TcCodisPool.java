package com.ysu.zyw.tc.components.cache.codis;

import io.codis.jodis.RoundRobinJedisPool;
import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.exceptions.JedisException;

import java.util.Objects;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

@Slf4j
public class TcCodisPool extends JedisPool {

    private final RoundRobinJedisPool roundRobinJedisPool;

    public TcCodisPool(String zkAddr,
                       int zkSessionTimeoutMs,
                       String zkProxyDir,
                       JedisPoolConfig codisPoolConfig,
                       int connectionTimeout,
                       String password) {
        super();
        checkNotNull(zkAddr, "empty zk addr is not allowed");
        checkArgument(zkSessionTimeoutMs > 0, "negative zk service timeout ms is not allowed");
        checkNotNull(zkProxyDir, "empty zk proxy dir is not allowed");
        checkNotNull(codisPoolConfig, "null codis pool config is not allowed");
        checkArgument(connectionTimeout > 0, "negative connection timeout is not allowed");
        if (Objects.isNull(password)) {
            log.warn("no password set to codis, it's dangerous");
        }
        log.info("init round robin jedis pool, zkAddr [{}], zkSessionTimeout [{}], zkProxyDir [{}]",
                zkAddr, zkSessionTimeoutMs, zkProxyDir);
        roundRobinJedisPool = RoundRobinJedisPool
                .create()
                .curatorClient(zkAddr, zkSessionTimeoutMs)
                .password(password)
                .zkProxyDir(zkProxyDir)
                .poolConfig(codisPoolConfig)
                .timeoutMs(connectionTimeout)
                .build();
    }

    @Override
    public Jedis getResource() {
        return roundRobinJedisPool.getResource();
    }

    @SuppressWarnings("deprecation")
    @Override
    public void returnBrokenResource(Jedis resource) {
        if (Objects.nonNull(resource)) {
            resource.close();
        }
    }

    @SuppressWarnings("deprecation")
    @Override
    public void returnResource(Jedis resource) {
        if (Objects.nonNull(resource)) {
            try {
                resource.resetState();
                resource.close();
            } catch (Exception e) {
                returnBrokenResource(resource);
                throw new JedisException("Could not return the resource to the pool", e);
            }
        }
    }

    @Override
    public void close() {
        super.close();
        roundRobinJedisPool.close();
        log.info("close codis pool (include round robin jedis pool)");
    }

}