package com.ysu.zyw.tc.base.utils;

import com.google.common.collect.Lists;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.helpers.MessageFormatter;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.google.common.base.Preconditions.checkArgument;
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

    public static String format(@Nonnull Object... args) {
        checkNotNull(args);
        List<String> strs = Lists.newArrayList();
        for (Object ignored : args) {
            strs.add("[{}]");
        }
        return format(strs.stream().collect(Collectors.joining(" ")), args);
    }

    private static final String OVER_LENGTH_DECORATE = "...";

    public static String maxLen(@Nonnull String value, int maxLen, @Nullable String decorate) {
        checkNotNull(value);
        checkArgument(maxLen > 0);
        if (value.length() > maxLen) {
            value = value.substring(0, maxLen - 1)
                    + (Objects.isNull(decorate) ? OVER_LENGTH_DECORATE : decorate);
        }
        return value;
    }

}
