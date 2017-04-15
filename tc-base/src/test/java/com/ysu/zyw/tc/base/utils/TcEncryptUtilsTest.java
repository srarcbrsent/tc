package com.ysu.zyw.tc.base.utils;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Assert;
import org.junit.Test;

import java.security.KeyPair;

public class TcEncryptUtilsTest {

    @Test
    public void testRSA() {
        String plain = RandomStringUtils.randomAscii(40);

        KeyPair keyPair = TcEncryptUtils.generateRSAKeyPair();
        String publicRSAKey = TcEncryptUtils.getPublicRSAKey(keyPair);
        String privateRSAKey = TcEncryptUtils.getPrivateRSAKey(keyPair);

        System.out.println(publicRSAKey);
        System.out.println(privateRSAKey);
        System.out.println();

        // encrypt
        String encrypt = TcEncryptUtils.encryptRSA(plain, publicRSAKey);
        System.out.println(encrypt);
        String decrypt = TcEncryptUtils.decryptRSA(encrypt, privateRSAKey);
        System.out.println(decrypt);
        Assert.assertEquals(plain, decrypt);

        // sign
        String sign = TcEncryptUtils.signRSA(plain, privateRSAKey);
        System.out.println(sign);
        String verify = TcEncryptUtils.verifyRSA(sign, publicRSAKey);
        System.out.println(verify);
        Assert.assertEquals(plain, verify);
    }

    @Test
    public void testAES() {
        String plainText = RandomStringUtils.randomAscii(40);

        String secretKey = TcEncryptUtils.createAESSecretKey();

        System.out.println(secretKey);

        // encrypt
        String encryptedText = TcEncryptUtils.encryptAES(plainText, secretKey);

        System.out.println(encryptedText);

        // decrypt
        String decryptedText = TcEncryptUtils.decryptAES(encryptedText, secretKey);

        System.out.println(decryptedText);

        Assert.assertEquals(plainText, decryptedText);
    }

}