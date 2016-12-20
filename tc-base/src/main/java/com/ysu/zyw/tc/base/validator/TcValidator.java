package com.ysu.zyw.tc.base.validator;

import org.apache.commons.validator.GenericValidator;

import java.util.Objects;

public class TcValidator extends GenericValidator {

    public static boolean isNull(Object value) {
        return Objects.isNull(value);
    }

    public static boolean nonNull(Object value) {
        return Objects.nonNull(value);
    }

    public static boolean nonBlankNonNull(String value) {
        return !GenericValidator.isBlankOrNull(value);
    }

    public static boolean isId(String value) {
        return nonNull(value) && GenericValidator.matchRegexp(value, "^[a-zA-Z0-9]{32}$");
    }

    public static boolean isMobile(String value) {
        return nonNull(value) && GenericValidator.matchRegexp(value, "^1[34578]\\d{9}$");
    }

    public static boolean isEmail(String value) {
        return nonNull(value) && GenericValidator.matchRegexp(value,
                "^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$");
    }

    public static boolean isRegion(String value) {
        return nonNull(value) && GenericValidator.matchRegexp(value, "^P[0-9]{2}-C[0-9]{3}-D[0-9]{4}$");
    }

}
