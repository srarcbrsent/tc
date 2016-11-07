package com.ysu.zyw.tc.components.cache;

import javax.annotation.Nonnull;
import java.io.Serializable;
import java.util.Set;
import java.util.concurrent.Callable;

public interface TcOpsForGroupedValue {

    void set(@Nonnull String group, @Nonnull String key, @Nonnull Serializable value, long timeout);

    <T> T get(@Nonnull String group, @Nonnull String key, @Nonnull Class<T> clazz);

    <T> T get(@Nonnull String group, @Nonnull String key, @Nonnull Callable<T> valueLoader, long timeout);

    boolean exists(@Nonnull String group, @Nonnull String key);

    void expire(@Nonnull String group, @Nonnull String key, long timeout);

    void delete(@Nonnull String group, @Nonnull String key);

    Set<String> keys(@Nonnull String group);

    void delete(@Nonnull String group);

}
