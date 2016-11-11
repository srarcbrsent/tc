package com.ysu.zyw.tc.model.validator;

import org.apache.commons.validator.GenericValidator;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.validation.Errors;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.google.common.base.Preconditions.checkNotNull;

public class TcValidator extends GenericValidator {

    // regexp
    private static final Pattern chineseCharPattern = Pattern.compile("[\\u4E00-\\u9FA5]");

    private static final Pattern englishCharPattern = Pattern.compile("[A-Za-z0-9_-]");

    private static final Pattern englishPattern = Pattern.compile("[A-Za-z0-9_-]+]");

    public static class TcVerifyFailure extends ArrayList<String> {

        public TcVerifyFailure(Errors errors) {
            checkNotNull(errors);
            if (errors.hasErrors()) {
                this.addAll(
                        errors.getAllErrors().stream()
                                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                                .collect(Collectors.toList())
                );
            }
        }

        public TcVerifyFailure(String... errors) {
            super(Arrays.asList(errors));
        }

    }

    public static boolean isNull(Object value) {
        return Objects.isNull(value);
    }

    public static boolean nonNull(Object value) {
        return Objects.nonNull(value);
    }

    public static boolean nonBlankOrNull(String value) {
        return !GenericValidator.isBlankOrNull(value);
    }

    public static boolean isSafeString(String value, int min, int max, boolean oneChineseAs2Char) {
        if (isNull(value)) {
            return false;
        }

        int chars = 0;
        for (String token : value.split("")) {
            if (englishCharPattern.matcher(token).matches()) {
                chars += 1;
            } else if (chineseCharPattern.matcher(token).matches()) {
                chars += oneChineseAs2Char ? 2 : 1;
            } else {
                return false;
            }
        }

        return chars >= min && chars <= max;
    }
    
    public static boolean isEnglish(String value, int min, int max) {
        if (isNull(value)) {
            return false;
        }

        if (value.length() < min || value.length() > max) {
            return false;
        }

        if (!englishPattern.matcher(value).matches()) {
            return false;
        }

        return true;
    }

    public static boolean isMobile(String value) {
        return nonNull(value) && GenericValidator.matchRegexp(value, "^1[34578]\\d{9}$");

    }

}
