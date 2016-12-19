package com.ysu.zyw.tc.components.commons.retry;

import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.RetryContext;
import org.springframework.retry.RetryListener;

/**
 * TcRetryLogListener is a listener for retry component and it will only log the exception.
 *
 * @author yaowu.zhang
 */
@Slf4j
public class TcRetryLogListener implements RetryListener {

    @Override
    public <T, E extends Throwable> boolean open(RetryContext context,
                                                 RetryCallback<T, E> callback) {
        if (log.isDebugEnabled()) {
            log.debug("start call retryable function [{}]", callback);
        }
        return true;
    }

    @Override
    public <T, E extends Throwable> void close(RetryContext context,
                                               RetryCallback<T, E> callback,
                                               Throwable throwable) {
        if (log.isDebugEnabled()) {
            log.debug("finish call retryable function [{}], total retry times [{}]", callback,
                    context.getRetryCount() + 1);
        }
    }

    @Override
    public <T, E extends Throwable> void onError(RetryContext context,
                                                 RetryCallback<T, E> callback,
                                                 Throwable throwable) {
        log.error("call retryable function [{}] failed after [{}] times call", callback, context.getRetryCount() + 1,
                throwable);
    }

}