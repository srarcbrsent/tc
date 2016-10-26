package com.ysu.zyw.tc.base.utils;

import lombok.SneakyThrows;

import java.security.MessageDigest;

public class TcEncodingUtils {

    private static final char[] HEX_DIGITS = { '0', '1', '2', '3', '4', '5',
            '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

    @SneakyThrows
    public static String md5(String text) {
        MessageDigest messageDigest = null;
        messageDigest = MessageDigest.getInstance("MD5");
        messageDigest.update(text.getBytes());
        byte[] bytes = messageDigest.digest();
        int len = bytes.length;
        StringBuilder sb = new StringBuilder(len * 2);
        for (byte aByte : bytes) {
            sb.append(HEX_DIGITS[(aByte >> 4) & 0x0f]);
            sb.append(HEX_DIGITS[aByte & 0x0f]);
        }
        return sb.toString();
    }

    @SneakyThrows
    public static String sha1(String text) {
        MessageDigest messageDigest = null;
        messageDigest = MessageDigest.getInstance("SHA1");
        messageDigest.update(text.getBytes());
        byte[] bytes = messageDigest.digest();
        int len = bytes.length;
        StringBuilder sb = new StringBuilder(len * 2);
        for (byte aByte : bytes) {
            sb.append(HEX_DIGITS[(aByte >> 4) & 0x0f]);
            sb.append(HEX_DIGITS[aByte & 0x0f]);
        }
        return sb.toString();
    }

}
