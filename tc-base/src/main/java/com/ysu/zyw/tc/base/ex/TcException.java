package com.ysu.zyw.tc.base.ex;

import com.ysu.zyw.tc.base.utils.TcFormatUtils;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * TcException is the base exception class in tc system. all exception must extends TcException.
 *
 * @author yaowu.zhang
 */
@Slf4j
public class TcException extends Exception {

    public TcException(@Nonnull String message, @Nullable Object... infos) {
        super(TcFormatUtils.format(message, infos));
    }

    public TcException(@Nonnull Throwable e, @Nullable Object... infos) {
        super(TcFormatUtils.format(infos), e);
    }

    public TcException(@Nonnull String message, @Nonnull Throwable e, @Nullable Object... infos) {
        super(TcFormatUtils.format(message, infos), e);
    }

}
