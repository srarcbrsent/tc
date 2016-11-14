package com.ysu.zyw.tc.platform.svc;

import com.ysu.zyw.tc.platform.fk.conf.TcConfig;
import com.ysu.zyw.tc.sys.constant.TcConstant;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;

@Service
public class TcVerificationCodeService {

    @Resource
    private TcConfig tcConfig;

    public String generateVerificationCodeAndSet2Session() {
        String verificationCode = RandomStringUtils.randomNumeric(6);
        if (BooleanUtils.isTrue(tcConfig.isFixedVerificationCode())) {
            verificationCode = "111111";
        }
        SecurityUtils.getSubject().getSession().setAttribute(TcConstant.S.SESSION_VERIFICATION_CODE, verificationCode);
        return verificationCode;
    }

    public boolean isVerificationCodeMatch(String verificationCode) {
        String verificationCodeInSession =
                (String) SecurityUtils.getSubject().getSession().getAttribute(TcConstant.S.SESSION_VERIFICATION_CODE);
        return Objects.nonNull(verificationCode) &&
                verificationCode.length() == 6 &&
                Objects.nonNull(verificationCodeInSession) &&
                verificationCodeInSession.length() == 6 &&
                Objects.equals(verificationCode, verificationCodeInSession);
    }

}
