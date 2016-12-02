package com.ysu.zyw.tc.base.ex;

import com.ysu.zyw.tc.base.utils.TcFormatUtils;
import lombok.extern.slf4j.Slf4j;

/**
 * TcException is the base exception class in tc system. all exception must extends TcException.
 *
 * @author yaowu.zhang
 */
@Slf4j
public class TcException extends RuntimeException {

    public TcException(String message, Object... infos) {
        super(TcFormatUtils.format(message, infos));
    }

    // only five obj supported
    public TcException(Throwable e, Object... infos) {
        super(TcFormatUtils.format("[{}] [{}] [{}] [{}] [{}]", infos), e);
    }

    public TcException(String message, Throwable e, Object... infos) {
        super(TcFormatUtils.format(message, infos), e);
    }

}
