package com.ysu.zyw.tc.openapi.svc;

import com.ysu.zyw.tc.base.utils.TcDateUtils;
import com.ysu.zyw.tc.openapi.constant.TcSessionKey;
import com.ysu.zyw.tc.openapi.fk.config.TcConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Objects;

@Slf4j
@Service
public class TcVerificationCodeService {

    @Resource
    private TcConfig tcConfig;

    private static final String FIXED_VERIFICATION_CODE = "111111";

    public String generateVerificationCodeAndSet2Session() {
        String verificationCode = RandomStringUtils.randomNumeric(6);
        if (BooleanUtils.isTrue(tcConfig.isFixedVerificationCode())) {
            verificationCode = FIXED_VERIFICATION_CODE;
        }
        String encodedVerificationCode = encodeVerificationCodeWithDatetime(verificationCode);
        SecurityUtils.getSubject().getSession()
                .setAttribute(TcSessionKey.INDEX_VERIFICATION_CODE, encodedVerificationCode);
        String sessionId = SecurityUtils.getSubject().getSession().getId().toString();
        log.info("session id -> [{}] verification code -> [{}]", sessionId, verificationCode);
        return verificationCode;
    }

    public boolean isVerificationCodeMatch(String verificationCode) {
        String encodedVerificationCodeInSession = (String) SecurityUtils.getSubject().getSession()
                .getAttribute(TcSessionKey.INDEX_VERIFICATION_CODE);
        if (Objects.isNull(encodedVerificationCodeInSession)) {
            return false;
        }
        String verificationCodeInSession = decodeVerificationCode(encodedVerificationCodeInSession);
        return Objects.equals(verificationCode, verificationCodeInSession);
    }

    private static final String VERIFICATION_CODE_AND_DATE_TIME_SEPARATIVE_SIGN = "___";

    private String encodeVerificationCodeWithDatetime(String verificationCode) {
        return TcDateUtils.format(new Date()) + VERIFICATION_CODE_AND_DATE_TIME_SEPARATIVE_SIGN + verificationCode;
    }

    private String decodeVerificationCode(String encodedVerificationCode) {
        return encodedVerificationCode.split(VERIFICATION_CODE_AND_DATE_TIME_SEPARATIVE_SIGN)[1];
    }

}
