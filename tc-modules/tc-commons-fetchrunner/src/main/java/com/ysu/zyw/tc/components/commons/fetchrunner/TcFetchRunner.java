package com.ysu.zyw.tc.components.commons.fetchrunner;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

import static com.google.common.base.Preconditions.checkNotNull;

@Slf4j
public class TcFetchRunner<T> {

    private Supplier<List<T>> supplier;

    private Consumer<T> consumer;

    public TcFetchRunner(@NonNull Supplier<List<T>> supplier,
                         @Nonnull Consumer<T> consumer) {
        checkNotNull(supplier);
        checkNotNull(consumer);
        this.supplier = supplier;
        this.consumer = consumer;
    }

    public void start() {
        List<T> datas = supplier.get();
        while (CollectionUtils.isNotEmpty(datas)) {
            datas.forEach(consumer);
            datas = supplier.get();
        }
    }


}
