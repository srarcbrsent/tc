package com.ysu.zyw.tc.components.exhandler;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TcUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        log.error("Unexpected errs occurred in scheduled task. thread [{}]", t.getName(), e);
    }

}
