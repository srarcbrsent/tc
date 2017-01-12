package com.ysu.zyw.tc.base.utils;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;

import javax.annotation.Nonnull;

import static com.google.common.base.Preconditions.checkNotNull;

@Slf4j
@UtilityClass
public class TcEncodingUtils {

    public static String md5(@Nonnull String text) {
        checkNotNull(text);
        return DigestUtils.md5Hex(text);
    }

    public static String sha1(@Nonnull String text) {
        checkNotNull(text);
        return DigestUtils.sha1Hex(text);
    }

    public static String sha256(@Nonnull String text) {
        checkNotNull(text);
        return DigestUtils.sha256Hex(text);
    }

    public static String encodeBase64(@Nonnull String text) {
        checkNotNull(text);
        return Base64.encodeBase64String(text.getBytes());
    }

    public static String dncodeBase64(@Nonnull String text) {
        checkNotNull(text);
        return new String(Base64.decodeBase64(text.getBytes()));
    }

}
