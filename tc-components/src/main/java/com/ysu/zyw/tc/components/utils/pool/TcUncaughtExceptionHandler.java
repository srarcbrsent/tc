package com.ysu.zyw.tc.components.utils.pool;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TcUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        log.error("Unexpected error occurred in scheduled task. thread [{}]", t.getName(), e);
    }

}
