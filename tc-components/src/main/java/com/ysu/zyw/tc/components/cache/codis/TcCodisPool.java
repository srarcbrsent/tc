package com.ysu.zyw.tc.components.cache.codis;

import com.google.common.base.Preconditions;
import io.codis.jodis.RoundRobinJedisPool;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.exceptions.JedisException;

import java.util.Objects;

public class TcCodisPool extends JedisPool {

    private final RoundRobinJedisPool roundRobinJedisPool;

    public TcCodisPool(String zkAddr,
                       int zkSessionTimeoutMs,
                       String zkProxyDir,
                       JedisPoolConfig codisPoolConfig,
                       int connectionTimeout,
                       String password) {
        super();

        Preconditions.checkNotNull(zkAddr, "empty zk addr is not allowed");
        Preconditions.checkArgument(zkSessionTimeoutMs > 0, "negative zk service timeout ms is not allowed");
        Preconditions.checkNotNull(zkProxyDir, "empty zk proxy dir is not allowed");
        Preconditions.checkNotNull(codisPoolConfig, "null codis pool config is not allowed");
        Preconditions.checkArgument(connectionTimeout > 0, "negative connection timeout is not allowed");
        Preconditions.checkNotNull(password, "codis password is required");
        roundRobinJedisPool = RoundRobinJedisPool.create().curatorClient(zkAddr, zkSessionTimeoutMs).password(password)
                .zkProxyDir(zkProxyDir).poolConfig(codisPoolConfig).timeoutMs(connectionTimeout).build();
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
    }

}