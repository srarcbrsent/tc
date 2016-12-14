package com.ysu.zyw.tc.model.validator;

import org.apache.commons.validator.GenericValidator;

import java.util.Objects;
import java.util.regex.Pattern;

public class TcValidator extends GenericValidator {

    // regexp
    private static final Pattern chineseCharPattern = Pattern.compile("[\\u4E00-\\u9FA5]");

    private static final Pattern englishCharPattern = Pattern.compile("[A-Za-z0-9_-]");

    private static final Pattern englishPattern = Pattern.compile("[A-Za-z0-9_-]+]");

    public static boolean isNull(Object value) {
        return Objects.isNull(value);
    }

    public static boolean nonNull(Object value) {
        return Objects.nonNull(value);
    }

    public static boolean nonBlankOrNull(String value) {
        return !GenericValidator.isBlankOrNull(value);
    }

    public static boolean isSafeString(String value, int min, int max) {
        return !isNull(value) && value.length() >= min && value.length() <= max;

    }

    public static boolean isMobile(String value) {
        return nonNull(value) && GenericValidator.matchRegexp(value, "^1[34578]\\d{9}$");

    }

}
