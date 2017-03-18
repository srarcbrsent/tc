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

    private static final String SIGNUP_VERIFICATION_CODE_EXPIRE_TIME_COUNTER_GROUP =
            "signup_verification_code_expire_time_counter_group";

    private static final long SIGNUP_VERIFICATION_CODE_EXPIRE_TIME_IN_SEC = 60;

    private static final String SIGNUP_RSA_KEY_EXPIRE_TIME_COUNTER_GROUP =
            "signup_rsa_key_expire_time_counter_group";

    private static final long SIGNUP_RSA_KEY_EXPIRE_TIME_IN_SEC = 15;

    private static final String SIGNUP_RSA_PUBLIC_KEY = "session_signup_rsa_public_key";

    private static final String SIGNUP_RSA_PRIVATE_KEY = "session_signup_rsa_private_key";

    @Resource(name = TcBeanNameConsts.SS_REDIS_SERVICE)
    private TcCacheService tcCacheService;

    public String generateVerificationCodeAndSet2Session() {
        String verificationCode = RandomStringUtils.randomNumeric(VERIFICATION_CODE_LENGTH);
        if (BooleanUtils.isTrue(tcConfig.isFixedVerificationCode())) {
            verificationCode = FIXED_VERIFICATION_CODE;
        }

        String sessionId = SecurityUtils.getSubject().getSession().getId().toString();
        String verificationCacheKey =
                tcCacheService.buildLogicKey(SIGNUP_VERIFICATION_CODE_EXPIRE_TIME_COUNTER_GROUP, sessionId);
        tcCacheService.set(verificationCacheKey, verificationCode, SIGNUP_VERIFICATION_CODE_EXPIRE_TIME_IN_SEC);

        log.info("session id -> [{}] verification code -> [{}]", sessionId, verificationCode);
        return verificationCode;
    }

    public boolean isVerificationCodeMatch(String verificationCode) {
        String sessionId = SecurityUtils.getSubject().getSession().getId().toString();
        String verificationCacheKey =
                tcCacheService.buildLogicKey(SIGNUP_VERIFICATION_CODE_EXPIRE_TIME_COUNTER_GROUP, sessionId);
        String verificationCodeInCache = tcCacheService.get(verificationCacheKey, new TypeReference<String>() {
        });
        return !Objects.isNull(verificationCodeInCache)
                && Objects.equals(verificationCode, verificationCodeInCache);
    }

    public String generateRSAKeyAndSet2Session() {
        KeyPair keyPair = TcEncryptUtils.generateKeyPair();
        String publicKey = TcEncryptUtils.getPublicRSAKey(keyPair);
        String privateKey = TcEncryptUtils.getPrivateRSAKey(keyPair);

        String sessionId = SecurityUtils.getSubject().getSession().getId().toString();
        String publicKeyCacheKey = tcCacheService.buildLogicKey(
                SIGNUP_RSA_KEY_EXPIRE_TIME_COUNTER_GROUP, SIGNUP_RSA_PUBLIC_KEY, sessionId);
        String privateKeyCacheKey = tcCacheService.buildLogicKey(
                SIGNUP_RSA_KEY_EXPIRE_TIME_COUNTER_GROUP, SIGNUP_RSA_PRIVATE_KEY, sessionId);

        tcCacheService.set(publicKeyCacheKey, publicKey, SIGNUP_RSA_KEY_EXPIRE_TIME_IN_SEC);
        tcCacheService.set(privateKeyCacheKey, privateKey, SIGNUP_RSA_KEY_EXPIRE_TIME_IN_SEC);

        log.info("session id -> [{}] successful generate rsa public & private key ...", sessionId);
        return publicKey;
    }

    public String decryptRSAEncryptedPassword(@Nonnull String rsaEncryptedPassword) {
        checkNotNull(rsaEncryptedPassword);

        String sessionId = SecurityUtils.getSubject().getSession().getId().toString();
        String privateKeyCacheKey = tcCacheService.buildLogicKey(
                SIGNUP_RSA_KEY_EXPIRE_TIME_COUNTER_GROUP, SIGNUP_RSA_PRIVATE_KEY, sessionId);

        String privateKey =
                tcCacheService.get(privateKeyCacheKey, new TypeReference<String>() {
                });
        if (Objects.isNull(privateKey)) {
            return null;
        }

        return TcEncryptUtils.decrypt(rsaEncryptedPassword, privateKey);
    }

}
