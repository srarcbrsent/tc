package com.ysu.zyw.tc.base.utils;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

import static com.google.common.base.Preconditions.checkNotNull;

@Slf4j
@UtilityClass
public class TcUtils {

    public static void doQuietly(@Nonnull TcTask tcTask) {
        checkNotNull(tcTask);
        try {
            tcTask.execute();
        } catch (Exception e) {
            log.error("", e);
        }
    }

    public static <R> R doQuietly(@Nonnull TcRTask<R> tcRTask,
                                  @Nullable R defaultValue) {
        checkNotNull(tcRTask);
        try {
            return tcRTask.execute();
        } catch (Exception e) {
            log.error("", e);
            return defaultValue;
        }
    }

    public static void doInDevMode(@Nonnull TcTask tcTask, boolean devMode) {
        if (devMode) {
            doQuietly(tcTask);
        }
    }

    public static <T1, T2, R1, R2> void match(@Nonnull List<T1> l1,
                                              @Nonnull Function<T1, R1> m1,
                                              @Nonnull List<T2> l2,
                                              @Nonnull Function<T2, R2> m2,
                                              @Nonnull BiConsumer<T1, T2> doWith) {
        match(
                l1,
                m1,
                l2,
                m2,
                Objects::equals,
                doWith
        );
    }

    public static <T1, T2, R1, R2> void match(@Nonnull List<T1> l1,
                                              @Nonnull Function<T1, R1> m1,
                                              @Nonnull List<T2> l2,
                                              @Nonnull Function<T2, R2> m2,
                                              @Nonnull BiFunction<R1, R2, Boolean> equals,
                                              @Nonnull BiConsumer<T1, T2> doWith) {
        l1.forEach(i1 -> {
            l2.forEach(i2 -> {
                if (equals.apply(m1.apply(i1), m2.apply(i2))) {
                    doWith.accept(i1, i2);
                }
            });
        });
    }

    @FunctionalInterface
    public interface TcTask {

        void execute();

    }

    @FunctionalInterface
    public interface TcRTask<R> {

        R execute();

    }

}
