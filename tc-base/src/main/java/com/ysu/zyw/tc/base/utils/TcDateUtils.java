package com.ysu.zyw.tc.base.utils;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;

import javax.annotation.Nonnull;
import java.util.Date;

import static com.google.common.base.Preconditions.checkNotNull;

@Slf4j
@UtilityClass
public class TcDateUtils {

    public static final String FULL_DATE_FORMAT_VALUE = "yyyy-MM-dd HH:mm:ss.SSS";

    public static final String SIMPLE_DATE_FORMAT_VALUE = "yyyy-MM-dd HH:mm:ss";

    public static final String[] DATE_FORMATS =
            {
                    FULL_DATE_FORMAT_VALUE,
                    SIMPLE_DATE_FORMAT_VALUE
            };

    @SneakyThrows
    public static Date parse(@Nonnull String text) {
        checkNotNull(text);
        return DateUtils.parseDate(text, DATE_FORMATS);
    }

    public static String format(@Nonnull Date date) {
        checkNotNull(date);
        return format(date, FULL_DATE_FORMAT_VALUE);
    }

    public static String format(@Nonnull Date date, @Nonnull String format) {
        checkNotNull(date);
        checkNotNull(format);
        return DateFormatUtils.format(date, format);
    }

    public static long duration(Date d1, Date d2) {
        checkNotNull(d1);
        checkNotNull(d2);
        return d2.getTime() - d1.getTime();
    }

}
