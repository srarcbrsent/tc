package com.ysu.zyw.tc.base.utils;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.helpers.MessageFormatter;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Objects;

import static com.google.common.base.Preconditions.checkNotNull;

@Slf4j
@UtilityClass
public class TcFormatUtils {

    public static String format(@Nonnull String format, @Nullable Object... args) {
        checkNotNull(format);
        if (Objects.isNull(args)) {
            return format;
        }
        return MessageFormatter.arrayFormat(format, args).getMessage();
    }

}
