package com.ysu.zyw.tc.fk.shiro;

import com.ysu.zyw.tc.base.utils.TcEncodingUtils;
import com.ysu.zyw.tc.sys.constant.TcConstant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;

import java.util.Objects;

import static com.google.common.base.Preconditions.checkArgument;

// client: cltPassword = md5(md5(salt + password) + onceToken)
// server: svrPassword = md5(dbPassword + onceToken)
// db    : dbPassword = md5(salt + password)
public class TcMd5Sha256CredentialsMatcher implements CredentialsMatcher {

    public String createTokenAndSet2Session() {
        String token = RandomStringUtils.randomNumeric(48);
        SecurityUtils.getSubject().getSession().setAttribute(TcConstant.S.SESSION_SHIRO_MATCHER_ONCE_TOKEN, token);
        return token;
    }

    // return: svrPassword
    public String encryptPassword(String dbPassword) {
        String token = doFindTokenOrThrow();
        return TcEncodingUtils.md5(dbPassword + token);
    }

    private String doFindTokenOrThrow() {
        Object tokenObject =
                SecurityUtils.getSubject().getSession().getAttribute(TcConstant.S.SESSION_SHIRO_MATCHER_ONCE_TOKEN);
        if (Objects.isNull(tokenObject)) {
            throw new IncorrectCredentialsException("do not have any token.");
        } else {
            // token can only be used once.
            SecurityUtils.getSubject().getSession().removeAttribute(TcConstant.S.SESSION_SHIRO_MATCHER_ONCE_TOKEN);
        }

        return tokenObject.toString();
    }

    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        checkArgument(token instanceof UsernamePasswordToken,
                "this matcher is only support username password token");

        String dbPassword = info.getCredentials().toString();
        String svrPassword = encryptPassword(dbPassword);

        String cltPassword = String.valueOf(token.getCredentials());
        // FIXME return Objects.equals(svrPassword, cltPassword);
        return true;
    }

    @Data
    @Accessors(chain = true)
    @AllArgsConstructor
    public static class TcTokenAndSalt {

        private String token;

        private String salt;

    }

}
