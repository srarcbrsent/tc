package com.ysu.zyw.tc.components.security.shiro;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationListener;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.PrincipalCollection;

public class TcLogAuthenticationListener implements AuthenticationListener {

    @Override
    public void onSuccess(AuthenticationToken token, AuthenticationInfo info) {

    }

    @Override
    public void onFailure(AuthenticationToken token, AuthenticationException ae) {

    }

    @Override
    public void onLogout(PrincipalCollection principals) {

    }

}
