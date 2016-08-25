package com.ysu.zyw.tc.sys.ex;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

/**
 * TcException is the base exception class in tc system. all exception must extends TcException.
 *
 * @author yaowu.zhang
 */
@Slf4j
public class TcException extends RuntimeException {

    public TcException(String message, Object... infos) {
        super(String.valueOf(message) + " " + Arrays.toString(infos));
    }

    public TcException(Throwable cause, Object... infos) {
        super(Arrays.toString(infos), cause);
    }

    public TcException(String message, Throwable cause, Object... infos) {
        super(String.valueOf(message) + " " + Arrays.toString(infos), cause);
    }

}
