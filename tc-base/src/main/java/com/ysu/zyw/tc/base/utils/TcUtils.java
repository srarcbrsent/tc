package com.ysu.zyw.tc.base.utils;

import lombok.extern.slf4j.Slf4j;

import static com.google.common.base.Preconditions.checkNotNull;

@Slf4j
public class TcUtils {

    public static void doQuietly(TcTask tcTask) {
        checkNotNull(tcTask);
        try {
            tcTask.execute();
        } catch (Exception e) {
            log.error("", e);
        }
    }

    public static <R> R doQuietly(TcRTask<R> tcRTask, R defaultValue) {
        checkNotNull(tcRTask);
        try {
            return tcRTask.execute();
        } catch (Exception e) {
            log.error("", e);
            return defaultValue;
        }
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
