package com.ysu.zyw.tc.components.cache.codis.ops;

import com.ysu.zyw.tc.components.cache.TcAbstractOpsForGroupedValue;
import com.ysu.zyw.tc.sys.ex.TcException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.serializer.RedisSerializer;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static com.google.common.base.Preconditions.checkNotNull;

@Slf4j
public class TcCodisOpsForGroupedValue extends TcAbstractOpsForGroupedValue implements ApplicationContextAware {

    // this is only for lazy init tc codis server delegate, only the first call of keys or delete
    // will lead to init tc codis server delegate, or sometimes this two api is useless, then
    // the codis server delegate will never be init.
    private ApplicationContext applicationContext;

    @SuppressWarnings("unchecked")
    @Override
    public Set<String> keys(@Nonnull String group) {
        checkNotNull(group, "empty group is not allowed");
        // try merge all the master codis-server keys.
        TcCodisServerDelegate tcCodisServerDelegate = applicationContext.getBean(TcCodisServerDelegate.class);
        Set<String> keys = new HashSet<>();
        tcCodisServerDelegate.doInEveryCodisServer(template -> {
            Set set = template.keys(GROUP_FIELD_PREFIX + group + GROUP_NAME_KEY_SPLIT + "*");
            keys.addAll(set);
        });
        return null;
    }

    @Override
    public void delete(@Nonnull String group) {
        checkNotNull(group, "empty group is not allowed");
        // TODO
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
