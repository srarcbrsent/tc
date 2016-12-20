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

    public static String format(@Nullable Object... args) {
        List<String> strs = Lists.newArrayList();
        for (Object ignored : args) {
            strs.add("[{}]");
        }
        return format(strs.stream().collect(Collectors.joining(" ")), args);
    }

}
