package com.ysu.zyw.tc.platform.fk.shiro;

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
            log.info("accounts login success, token principal [{}].", token.getPrincipal());
        }
    }

    @Override
    public void onFailure(AuthenticationToken token, AuthenticationException ae) {
        log.info("accounts [{}] authentication failure.", token.getPrincipal(), ae);
    }

    @Override
    public void onLogout(PrincipalCollection principals) {
        if (log.isInfoEnabled()) {
            log.info("accounts logout success, primary principal [{}].", principals.getPrimaryPrincipal());
        }
    }

}
