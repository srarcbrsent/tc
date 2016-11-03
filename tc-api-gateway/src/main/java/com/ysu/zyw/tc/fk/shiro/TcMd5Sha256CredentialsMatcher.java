package com.ysu.zyw.tc.fk.shiro;

import com.ysu.zyw.tc.sys.constant.TcConstant;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.crypto.hash.Sha256Hash;

import java.util.Objects;

import static com.google.common.base.Preconditions.checkArgument;

public class TcMd5Sha256CredentialsMatcher implements CredentialsMatcher {

    private static final String SHIRO_MATCHER_SHA1_SALT = "Bf7MfkNR0axGGptozrebag==Bf7MfkNR0axGGptozrebag==";

    public String createTokenAndSet2Session() {
        String token = RandomStringUtils.random(48);
        SecurityUtils.getSubject().getSession().setAttribute(TcConstant.S.SESSION_SHIRO_MATCHER_ONCE_TOKEN, token);
        return token;
    }

    // db: sha1(md5(plainPassword) + salt)
    // client: md5Password = onceToken + md5(plainPassword)
    // server: sha256Password = sha1(md5Password + salt)
    public String encryptPassword(String uMd5PasswordAndToken) {
        String uMd5Password = returnValidCredentialsOrThrow(uMd5PasswordAndToken);
        return Sha256Hash.toString((uMd5Password + SHIRO_MATCHER_SHA1_SALT).getBytes());
    }

    private String returnValidCredentialsOrThrow(String uMd5PasswordAndToken) {
        Object tokenObject =
                SecurityUtils.getSubject().getSession().getAttribute(TcConstant.S.SESSION_SHIRO_MATCHER_ONCE_TOKEN);
        if (Objects.isNull(tokenObject) ||
                !uMd5PasswordAndToken.startsWith(tokenObject.toString())) {
            throw new IncorrectCredentialsException();
        } else {
            // token can only be used once.
            SecurityUtils.getSubject().getSession().removeAttribute(TcConstant.S.SESSION_SHIRO_MATCHER_ONCE_TOKEN);
        }

        String uMd5Password = uMd5PasswordAndToken.substring(tokenObject.toString().length());
        checkArgument(uMd5Password.length() == Md5Hash.toString("0".getBytes()).length());
        return uMd5Password;
    }

    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        checkArgument(token instanceof UsernamePasswordToken,
                "this matcher is only support username password token");

        String uMd5Password = info.getCredentials().toString();
        String uEncryptPassword = encryptPassword(uMd5Password);

        String dbEncryptPassword = String.valueOf(token.getCredentials());
        return Objects.equals(uEncryptPassword, dbEncryptPassword);
    }

}
