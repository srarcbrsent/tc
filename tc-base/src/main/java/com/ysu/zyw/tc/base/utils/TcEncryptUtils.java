package com.ysu.zyw.tc.base.utils;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Nonnull;
import javax.crypto.Cipher;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;

import static com.google.common.base.Preconditions.checkNotNull;

@Slf4j
@UtilityClass
public class TcEncryptUtils {

    private static final String RSA_ALGORITHM = "RSA";

    private static final int RSA_ALGORITHM_KEY_SIZE = 1024;

    @SneakyThrows
    public static KeyPair generateRSAPair() {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(RSA_ALGORITHM);
        keyPairGenerator.initialize(RSA_ALGORITHM_KEY_SIZE);
        return keyPairGenerator.generateKeyPair();
    }

    @SneakyThrows
    public static String encryptRSA(@Nonnull PublicKey publicKey, @Nonnull String text) {
        checkNotNull(publicKey);
        checkNotNull(text);
        Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] cipherBytes = cipher.doFinal(text.getBytes());
        return new String(cipherBytes);
    }

    @SneakyThrows
    public static String decryptRSA(@Nonnull PrivateKey privateKey, @Nonnull String text) {
        checkNotNull(privateKey);
        checkNotNull(text);
        Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] plainBytes = cipher.doFinal(text.getBytes());
        return new String(plainBytes);
    }

}
