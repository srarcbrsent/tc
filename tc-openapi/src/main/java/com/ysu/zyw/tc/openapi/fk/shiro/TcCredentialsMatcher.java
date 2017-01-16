package com.ysu.zyw.tc.openapi.fk.shiro;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;

import java.util.Objects;

import static com.google.common.base.Preconditions.checkArgument;

// client: cltPassword = sha1(password)
// db    : dbPassword = sha1(password)
public class TcCredentialsMatcher implements CredentialsMatcher {

    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        checkArgument(token instanceof UsernamePasswordToken,
                "this matcher is only support username password token");

        String dbPassword = info.getCredentials().toString();

        String cltPassword = new String((char[]) token.getCredentials());
        return Objects.equals(dbPassword, cltPassword);
    }

}
