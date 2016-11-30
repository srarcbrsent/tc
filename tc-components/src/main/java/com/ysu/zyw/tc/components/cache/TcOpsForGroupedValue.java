package com.ysu.zyw.tc.components.cache;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.Serializable;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.locks.ReentrantLock;

public interface TcOpsForGroupedValue {

    void set(@Nonnull String group,
             @Nonnull String key,
             @Nonnull Serializable value,
             long timeout);

    <T> T get(@Nonnull String group,
              @Nonnull String key,
              @Nonnull Class<T> clazz);

    <T> T get(@Nonnull String group,
              @Nonnull String key,
              @Nonnull Callable<T> valueLoader,
              long timeout,
              @Nullable final ReentrantLock lock);

    boolean exists(@Nonnull String group,
                   @Nonnull String key);

    void expire(@Nonnull String group,
                @Nonnull String key,
                long timeout);

    void delete(@Nonnull String group,
                @Nonnull String key);

    Set<String> keys(@Nonnull String group);

    void delete(@Nonnull String group);

}
