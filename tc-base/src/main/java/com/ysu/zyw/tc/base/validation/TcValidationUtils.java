package com.ysu.zyw.tc.base.validation;

import com.google.common.collect.Lists;
import org.apache.commons.validator.GenericValidator;

import javax.validation.ConstraintViolation;
import java.net.IDN;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static java.util.regex.Pattern.CASE_INSENSITIVE;

public class TcValidationUtils extends GenericValidator {

    public static boolean isNull(Object value) {
        return Objects.isNull(value);
    }

    public static boolean nonNull(Object value) {
        return Objects.nonNull(value);
    }

    public static boolean nonNullNonBlank(String value) {
        return !GenericValidator.isBlankOrNull(value);
    }

    public static boolean isId(String value) {
        return nonNull(value) && GenericValidator.matchRegexp(value, "^[a-zA-Z0-9]{32}$");
    }

    public static boolean isMobile(String value) {
        return nonNull(value) && GenericValidator.matchRegexp(value, "^1[34578]\\d{9}$");
    }

    public static boolean isEmail(String value) {
        if (Objects.isNull(value)) {
            return false;
        }

        // see hibernate validator
        String[] emailParts = value.split("@", 3);
        return emailParts.length == 2 && matchLocalPart(emailParts[0]) && matchDomain(emailParts[1]);
    }

    public static boolean isRegion(String value) {
        return nonNull(value) && GenericValidator.matchRegexp(value, "^P[0-9]{2}-C[0-9]{3}-D[0-9]{4}$");
    }

    public static boolean isUrl(String value) {
        if (Objects.isNull(value)) {
            return false;
        }

        // see hibernate validator
        try {
            new URL(value);
            return true;
        } catch (MalformedURLException e) {
            return false;
        }
    }

    public static boolean isEnglishStr(String value, int min, int max) {
        return nonNull(value)
                && GenericValidator.matchRegexp(value, "^[_a-zA-Z0-9]{" + min + "," + max + "}");
    }

    public static boolean isNormalStr(String value, int min, int max) {
        return nonNull(value)
                && GenericValidator.matchRegexp(value, "^[\\-_a-zA-Z0-9\u4e00-\u9fa5]{" + min + "," + max + "}");
    }

    public static boolean isRichText(String value) {
        return nonNull(value)
                && GenericValidator.matchRegexp(value, "^[/\"<>~!@#$%^&*()-=_a-zA-Z0-9\u4e00-\u9fa5]+");
    }

    public static List<String> unwrap(Set<ConstraintViolation<? extends Object>> violations) {
        if (Objects.isNull(violations)) {
            return Lists.newArrayList();
        } else {
            return violations.stream().map(ConstraintViolation::getMessage).collect(Collectors.toList());
        }
    }

    /**
     * -------------------------------- email validation, see hibernate validator --------------------------------
     */
    private static final String LOCAL_PART_ATOM = "[a-z0-9!#$%&'*+/=?^_`{|}~\u0080-\uFFFF-]";

    private static final String DOMAIN_LABEL = "[a-z0-9!#$%&'*+/=?^_`{|}~-]";

    private static final String DOMAIN = DOMAIN_LABEL + "+(\\." + DOMAIN_LABEL + "+)*";

    private static final String IP_DOMAIN = "\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}]";

    private static final int MAX_LOCAL_PART_LENGTH = 64;

    private static final int MAX_DOMAIN_PART_LENGTH = 255;

    private static final Pattern LOCAL_PART_PATTERN = Pattern.compile(
            LOCAL_PART_ATOM + "+(\\." + LOCAL_PART_ATOM + "+)*", CASE_INSENSITIVE
    );

    private static final Pattern DOMAIN_PATTERN = Pattern.compile(
            DOMAIN + "|" + IP_DOMAIN, CASE_INSENSITIVE
    );

    private static boolean matchLocalPart(String localPart) {
        if (localPart.length() > MAX_LOCAL_PART_LENGTH) {
            return false;
        }
        Matcher matcher = LOCAL_PART_PATTERN.matcher(localPart);
        return matcher.matches();
    }

    private static boolean matchDomain(String domain) {
        // if we have a trailing dot the domain part we have an invalid email address.
        // the regular expression match would take care of this, but IDN.toASCII drops the trailing '.'
        if (domain.endsWith(".")) {
            return false;
        }

        String asciiString;
        try {
            asciiString = IDN.toASCII(domain);
        } catch (IllegalArgumentException e) {
            return false;
        }

        if (asciiString.length() > MAX_DOMAIN_PART_LENGTH) {
            return false;
        }

        Matcher matcher = DOMAIN_PATTERN.matcher(asciiString);
        return matcher.matches();
    }

}
