package com.ysu.zyw.tc.openapi.svc;

import com.fasterxml.jackson.core.type.TypeReference;
import com.ysu.zyw.tc.base.constant.TcBeanNameConsts;
import com.ysu.zyw.tc.base.utils.TcEncryptUtils;
import com.ysu.zyw.tc.components.cache.api.TcCacheService;
import com.ysu.zyw.tc.openapi.fk.config.TcConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;
import javax.annotation.Resource;
import java.security.KeyPair;
import java.util.Objects;

import static com.google.common.base.Preconditions.checkNotNull;

@Slf4j
@Service
public class TcAuthenticationService {

    @Resource
    private TcConfig tcConfig;

    private static final int VERIFICATION_CODE_LENGTH = 6;

    private static final String FIXED_VERIFICATION_CODE = "111111";

    private static final String LOGIN_VERIFICATION_CODE_EXPIRE_TIME_COUNTER_GROUP =
            "login_verification_code_expire_time_counter_group";

    private static final long LOGIN_VERIFICATION_CODE_EXPIRE_TIME_IN_MS = 60000L;

    private static final String LOGIN_RSA_KEY_EXPIRE_TIME_COUNTER_GROUP =
            "login_rsa_key_expire_time_counter_group";

    private static final long LOGIN_RSA_KEY_EXPIRE_TIME_IN_MS = 15000L;

    private static final String LOGIN_ENCRYPT_PASSWORD_RSA_PUBLIC_KEY =
            "session_login_encrypt_password_rsa_public_key";

    private static final String LOGIN_ENCRYPT_PASSWORD_RSA_PRIVATE_KEY =
            "session_login_encrypt_password_rsa_private_key";

    @Resource(name = TcBeanNameConsts.SS_REDIS_SERVICE)
    private TcCacheService tcCacheService;

    public String generateVerificationCodeAndSet2Cache() {
        String verificationCode = RandomStringUtils.randomNumeric(VERIFICATION_CODE_LENGTH);
        if (BooleanUtils.isTrue(tcConfig.isFixedVerificationCode())) {
            verificationCode = FIXED_VERIFICATION_CODE;
        }

        String sessionId = SecurityUtils.getSubject().getSession().getId().toString();
        String verificationCacheKey =
                tcCacheService.buildLogicKey(LOGIN_VERIFICATION_CODE_EXPIRE_TIME_COUNTER_GROUP, sessionId);
        tcCacheService.set(verificationCacheKey, verificationCode, LOGIN_VERIFICATION_CODE_EXPIRE_TIME_IN_MS);

        log.info("session id -> [{}] verification code -> [{}]", sessionId, verificationCode);
        return verificationCode;
    }

    public String getVerificationCodeInCache() {
        String sessionId = SecurityUtils.getSubject().getSession().getId().toString();
        String verificationCacheKey =
                tcCacheService.buildLogicKey(LOGIN_VERIFICATION_CODE_EXPIRE_TIME_COUNTER_GROUP, sessionId);
        return tcCacheService.get(verificationCacheKey, new TypeReference<String>() {
        });
    }

    public boolean isVerificationCodeMatch(String verificationCodeInCache, String verificationCode) {
        return !Objects.isNull(verificationCodeInCache)
                && Objects.equals(verificationCode, verificationCodeInCache);
    }

    public String generateRSAKeyAndSet2Session() {
        KeyPair keyPair = TcEncryptUtils.generateRSAKeyPair();
        String publicKey = TcEncryptUtils.getPublicRSAKey(keyPair);
        String privateKey = TcEncryptUtils.getPrivateRSAKey(keyPair);

        String sessionId = SecurityUtils.getSubject().getSession().getId().toString();
        String publicKeyCacheKey = tcCacheService.buildLogicKey(
                LOGIN_RSA_KEY_EXPIRE_TIME_COUNTER_GROUP, LOGIN_ENCRYPT_PASSWORD_RSA_PUBLIC_KEY, sessionId);
        String privateKeyCacheKey = tcCacheService.buildLogicKey(
                LOGIN_RSA_KEY_EXPIRE_TIME_COUNTER_GROUP, LOGIN_ENCRYPT_PASSWORD_RSA_PRIVATE_KEY, sessionId);

        tcCacheService.set(publicKeyCacheKey, publicKey, LOGIN_RSA_KEY_EXPIRE_TIME_IN_MS);
        tcCacheService.set(privateKeyCacheKey, privateKey, LOGIN_RSA_KEY_EXPIRE_TIME_IN_MS);

        log.info("session id -> [{}] successfully generate rsa public & private key ...", sessionId);
        return publicKey;
    }

    public String decryptRSAEncryptedPassword(@Nonnull String encryptedPassword) {
        checkNotNull(encryptedPassword);

        String sessionId = SecurityUtils.getSubject().getSession().getId().toString();
        String privateKeyCacheKey = tcCacheService.buildLogicKey(
                LOGIN_RSA_KEY_EXPIRE_TIME_COUNTER_GROUP, LOGIN_ENCRYPT_PASSWORD_RSA_PRIVATE_KEY, sessionId);

        String privateKey = tcCacheService.get(privateKeyCacheKey, new TypeReference<String>() {
        });
        if (Objects.isNull(privateKey)) {
            return null;
        }

        return TcEncryptUtils.decryptRSA(encryptedPassword, privateKey);
    }

}
