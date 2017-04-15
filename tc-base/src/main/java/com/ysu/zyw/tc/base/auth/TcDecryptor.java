package com.ysu.zyw.tc.base.auth;

import com.ysu.zyw.tc.base.utils.TcEncryptUtils;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TcDecryptor {

    private static final String ENVIRONMENT_SECRET_KEY = "secretKey";

    public static String decrypt(String encryptedText) {
        String secretKey = System.getProperty(ENVIRONMENT_SECRET_KEY);
        log.info("start decrypt text, length [{}] ...", encryptedText.length());
        return TcEncryptUtils.decryptAES(encryptedText, secretKey);
    }

    public static void main(String[] args) {
        String accessKeyId = "";
        String accessKeySecret = "";
        String secretKey = TcEncryptUtils.createAESSecretKey();
        System.out.println(secretKey);
        System.out.println(TcEncryptUtils.encryptAES(accessKeyId, secretKey));
        System.out.println(TcEncryptUtils.encryptAES(accessKeySecret, secretKey));
    }

}
