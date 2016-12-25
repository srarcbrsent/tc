package com.ysu.zyw.tc.platform.fk.shiro;

import com.ysu.zyw.tc.base.constant.TcConstant;
import com.ysu.zyw.tc.base.utils.TcEncodingUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;

import java.util.Objects;

import static com.google.common.base.Preconditions.checkArgument;

// client: cltPassword = md5(md5(password) + onceToken)
// server: svrPassword = md5(dbPassword + onceToken)
// db    : dbPassword = md5(password)
public class TcCredentialsMatcher implements CredentialsMatcher {

    public String createTokenAndSet2Session() {
        String token = RandomStringUtils.randomNumeric(48);
        SecurityUtils.getSubject().getSession().setAttribute(TcConstant.SessionKey.SHIRO_MATCHER_ONCE_TOKEN, token);
        return token;
    }

    // return: svrPassword
    public String encryptPassword(String dbPassword) {
        String token = doFindTokenOrThrow();
        return TcEncodingUtils.md5(dbPassword + token);
    }

    private String doFindTokenOrThrow() {
        Object tokenObject =
                SecurityUtils.getSubject().getSession().getAttribute(TcConstant.SessionKey.SHIRO_MATCHER_ONCE_TOKEN);
        if (Objects.isNull(tokenObject)) {
            throw new IncorrectCredentialsException("do not have any token.");
        } else {
            // token can only be used once.
            SecurityUtils.getSubject().getSession().removeAttribute(TcConstant.SessionKey.SHIRO_MATCHER_ONCE_TOKEN);
        }

        return tokenObject.toString();
    }

    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        checkArgument(token instanceof UsernamePasswordToken,
                "this matcher is only support username password token");

        String dbPassword = info.getCredentials().toString();
        String svrPassword = encryptPassword(dbPassword);

        String cltPassword = new String((char[]) token.getCredentials());
        return Objects.equals(svrPassword, cltPassword);
    }

}