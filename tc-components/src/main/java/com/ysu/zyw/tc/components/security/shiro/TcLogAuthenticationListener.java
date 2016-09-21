package com.ysu.zyw.tc.components.security.shiro;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationListener;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.PrincipalCollection;

@Slf4j
public class TcLogAuthenticationListener implements AuthenticationListener {

    @Override
    public void onSuccess(AuthenticationToken token, AuthenticationInfo info) {
        if (log.isInfoEnabled()) {
            log.info("account [{}] login success.", token.getPrincipal());
        }
    }

    @Override
    public void onFailure(AuthenticationToken token, AuthenticationException ae) {
        if (log.isInfoEnabled()) {
            log.info("account [{}] login failure.", token.getPrincipal());
        }
    }

    @Override
    public void onLogout(PrincipalCollection principals) {
        if (log.isInfoEnabled()) {
            log.info("account [{}] logout success.", principals.getPrimaryPrincipal());
        }
    }

}
