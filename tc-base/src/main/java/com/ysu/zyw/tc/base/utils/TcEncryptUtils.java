package com.ysu.zyw.tc.base.utils;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.RandomStringUtils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.annotation.Nonnull;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

@SuppressWarnings("Duplicates")
@Slf4j
@UtilityClass
public class TcEncryptUtils {

    // RSA

    private static final String RSA_ALGORITHM = "RSA";

    private static final String RSA_ALGORITHM_WITH_PADDING = "RSA/None/PKCS1Padding";

    private static final int KEY_SIZE = 1024;

    private static final int MAX_DATA_LENGTH = 40;

    private static final Provider PROVIDER = new BouncyCastleProvider();

    @SneakyThrows
    public static KeyPair generateRSAKeyPair() {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(RSA_ALGORITHM, PROVIDER);
        keyPairGenerator.initialize(KEY_SIZE, new SecureRandom(RandomStringUtils.randomAscii(8).getBytes()));
        return keyPairGenerator.generateKeyPair();
    }

    public static String getPublicRSAKey(@Nonnull KeyPair keyPair) {
        checkNotNull(keyPair);
        return Base64.encodeBase64String(keyPair.getPublic().getEncoded());
    }

    public static String getPrivateRSAKey(@Nonnull KeyPair keyPair) {
        checkNotNull(keyPair);
        return Base64.encodeBase64String(keyPair.getPrivate().getEncoded());
    }

    @SneakyThrows
    public static PublicKey getPublicRSAKey(@Nonnull String publicKey) {
        checkNotNull(publicKey);
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(Base64.decodeBase64(publicKey));
        KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM, PROVIDER);
        return keyFactory.generatePublic(x509EncodedKeySpec);
    }

    @SneakyThrows
    public static PrivateKey getPrivateRSAKey(@Nonnull String privateKey) {
        checkNotNull(privateKey);
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(Base64.decodeBase64(privateKey));
        KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM, PROVIDER);
        return keyFactory.generatePrivate(pkcs8EncodedKeySpec);
    }

    @SneakyThrows
    public static String encryptRSA(@Nonnull String plain, @Nonnull String publicKey) {
        checkNotNull(plain);
        checkNotNull(publicKey);
        checkArgument(plain.length() <= MAX_DATA_LENGTH, "too much data for RSA block");
        return encryptRSA(plain, getPublicRSAKey(publicKey));
    }

    @SneakyThrows
    public static String encryptRSA(@Nonnull String plain, @Nonnull PublicKey publicKey) {
        checkNotNull(plain);
        checkNotNull(publicKey);
        checkArgument(plain.length() <= MAX_DATA_LENGTH, "too much data for RSA block");
        Cipher cipher = Cipher.getInstance(RSA_ALGORITHM_WITH_PADDING, PROVIDER);
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        return Base64.encodeBase64String(cipher.doFinal(plain.getBytes(StandardCharsets.UTF_8)));
    }

    @SneakyThrows
    public static String decryptRSA(@Nonnull String encrypted, @Nonnull String privateKey) {
        checkNotNull(encrypted);
        checkNotNull(privateKey);
        return decryptRSA(encrypted, getPrivateRSAKey(privateKey));
    }

    @SneakyThrows
    public static String decryptRSA(@Nonnull String encrypted, @Nonnull PrivateKey privateKey) {
        checkNotNull(encrypted);
        checkNotNull(privateKey);
        Cipher cipher = Cipher.getInstance(RSA_ALGORITHM_WITH_PADDING, PROVIDER);
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        return new String(cipher.doFinal(Base64.decodeBase64(encrypted)));
    }

    @SneakyThrows
    public static String signRSA(@Nonnull String plain, @Nonnull String privateKey) {
        checkNotNull(plain);
        checkNotNull(privateKey);
        checkArgument(plain.length() <= MAX_DATA_LENGTH, "too much data for RSA block");
        return signRSA(plain, getPrivateRSAKey(privateKey));
    }

    @SneakyThrows
    public static String signRSA(@Nonnull String plain, @Nonnull PrivateKey privateKey) {
        checkNotNull(plain);
        checkNotNull(privateKey);
        checkArgument(plain.length() <= MAX_DATA_LENGTH, "too much data for RSA block");
        Cipher cipher = Cipher.getInstance(RSA_ALGORITHM_WITH_PADDING, PROVIDER);
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);
        return Base64.encodeBase64String(cipher.doFinal(plain.getBytes(StandardCharsets.UTF_8)));
    }

    @SneakyThrows
    public static String verifyRSA(@Nonnull String encrypted, @Nonnull String publicKey) {
        checkNotNull(encrypted);
        checkNotNull(publicKey);
        return verifyRSA(encrypted, getPublicRSAKey(publicKey));
    }

    @SneakyThrows
    public static String verifyRSA(@Nonnull String encrypted, @Nonnull PublicKey publicKey) {
        checkNotNull(encrypted);
        checkNotNull(publicKey);
        Cipher cipher = Cipher.getInstance(RSA_ALGORITHM_WITH_PADDING, PROVIDER);
        cipher.init(Cipher.DECRYPT_MODE, publicKey);
        return new String(cipher.doFinal(Base64.decodeBase64(encrypted)));
    }

    // AES

    private static final String AES_ALGORITHM = "AES";

    private static final int AES_CIPHER_LENGTH = 128;

    private static final String AES_CIPHER_ALGORITHM = "AES/ECB/PKCS5Padding";

    @SneakyThrows
    public static String createAESSecretKey() {
        KeyGenerator kg = KeyGenerator.getInstance(AES_ALGORITHM);
        kg.init(AES_CIPHER_LENGTH);
        SecretKey secretKey = kg.generateKey();
        return Base64.encodeBase64String(secretKey.getEncoded());
    }

    @SneakyThrows
    public static String encryptAES(@Nonnull String plainText, @Nonnull String secretKey) {
        checkNotNull(plainText);
        checkNotNull(secretKey);
        Key key = new SecretKeySpec(Base64.decodeBase64(secretKey), AES_ALGORITHM);
        Cipher cipher = Cipher.getInstance(AES_CIPHER_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        return Base64.encodeBase64String(cipher.doFinal(plainText.getBytes()));
    }

    @SneakyThrows
    public static String decryptAES(@Nonnull String encryptedText, @Nonnull String secretKey) {
        checkNotNull(encryptedText);
        checkNotNull(secretKey);
        Key key = new SecretKeySpec(Base64.decodeBase64(secretKey), AES_ALGORITHM);
        Cipher cipher = Cipher.getInstance(AES_CIPHER_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, key);
        return new String(cipher.doFinal(Base64.decodeBase64(encryptedText)));
    }

}
