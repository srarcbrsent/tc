package com.ysu.zyw.tc.base.tool;

import javax.annotation.Nonnull;
import java.util.regex.Pattern;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Validator.
 *
 * @author yaowu.zhang
 */
public class Validator {

    public static final Pattern PATTERN_CHINESE = Pattern.compile("^[\\u4e00-\\u9fa5]+$");

    public static final Pattern PATTERN_LONGHAND = Pattern.compile("^[\\u4E00-\\u9FA5A-Za-z0-9_]+$");

    public static final Pattern PATTERN_EMAIL = Pattern.compile("^([a-z0-9A-Z]+[-|.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+" +
            "(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$");

    public static final Pattern PATTERN_MOBILE = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(17[6,7,8])|(18[0,2,5-9]))" +
            "\\d{8}$");

    public static final Pattern PATTERN_DATETIME = Pattern.compile("^\\d{4}-\\d{1,2}-\\d{1,2}\\s\\d{1,2}:\\d{1," +
            "2}:\\d{1,2}");

    public static boolean matchPattern(@Nonnull String text, @Nonnull Pattern pattern) {
        checkNotNull(text, "null text is not allowed");
        checkNotNull(pattern, "null pattern is not allowed");
        return pattern.matcher(text).find();
    }

}
