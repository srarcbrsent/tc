package com.ysu.zyw.tc.components.cache;

import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.Serializable;
import java.util.concurrent.Callable;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Function;

public interface TcCacheService {

    <R> R doInCache(@Nonnull Function<RedisTemplate, R> task);

    void set(@Nonnull String key,
             @Nonnull Serializable value,
             long timeout);

    <T extends Serializable> T get(@Nonnull String key,
                                   @Nonnull Class<T> clazz);

    <T extends Serializable> T get(@Nonnull String key,
                                   @Nonnull Callable<T> valueLoader,
                                   long timeout,
                                   @Nullable final ReentrantLock lock);

    boolean exists(@Nonnull String key);

    void expire(@Nonnull String key,
                long timeout);

    void delete(@Nonnull String key);

    String buildKey(@Nonnull String group,
                    @Nonnull String...keys);

    String buildHashtag(@Nonnull String hashtag,
                        @Nonnull String key);

    TcOpsForGroupedValue opsForGroupedValue();

}
