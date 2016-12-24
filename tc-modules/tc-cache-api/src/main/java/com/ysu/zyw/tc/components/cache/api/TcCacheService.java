package com.ysu.zyw.tc.components.cache.api;

import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.concurrent.Callable;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Function;

public interface TcCacheService {

    <R> R doInCache(@Nonnull Function<RedisTemplate, R> task);

    void set(@Nonnull String key, @Nonnull Object value, long timeout);

    <T> T get(@Nonnull String key, @Nonnull TypeReference<T> typeReference);

    <T> T get(@Nonnull String key, @Nonnull Callable<T> valueLoader, long timeout, @Nullable ReentrantLock lock);

    boolean exists(@Nonnull String key);

    void expire(@Nonnull String key, long timeout);

    void delete(@Nonnull String key);

    String buildLogicKey(@Nonnull String group, @Nonnull String... keys);

    String buildHashtag(@Nonnull String hashtag, @Nonnull String key);

    TcOpsForGroupedValue opsForGroupedValue();

}
