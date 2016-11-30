package com.ysu.zyw.tc.components.cache;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.Serializable;
import java.util.concurrent.Callable;
import java.util.concurrent.locks.ReentrantLock;

public interface TcCacheService {

    void set(@Nonnull String key,
             @Nonnull Serializable value,
             long timeout);

    <T> T get(@Nonnull String key,
              @Nonnull Class<T> clazz);

    <T> T get(@Nonnull String key,
              @Nonnull Callable<T> valueLoader,
              long timeout,
              @Nullable final ReentrantLock lock);

    boolean exists(@Nonnull String key);

    void expire(@Nonnull String key,
                long timeout);

    void delete(@Nonnull String key);

    String buildHashtag(@Nonnull String hashtag,
                        @Nonnull String key);

    TcOpsForGroupedValue opsForGroupedValue();

}
