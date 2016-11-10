package com.ysu.zyw.tc.api.impl.accounts.auth;

import com.ysu.zyw.tc.api.TcAuthenticationApi;
import com.ysu.zyw.tc.api.svc.accounts.TcAccountService;
import com.ysu.zyw.tc.api.svc.accounts.auth.TcAuthService;
import com.ysu.zyw.tc.model.accounts.auth.TmPermission;
import com.ysu.zyw.tc.mw.TcP;
import com.ysu.zyw.tc.mw.TcR;

import javax.annotation.Resource;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.FormParam;
import java.util.List;

public class TcAuthenticationApiImpl implements TcAuthenticationApi {

    @Resource
    private TcAuthService tcAuthService;

    @Resource
    private TcAccountService tcAccountService;

    @Override
    public TcR<String, Void> canSignup(
            @FormParam(value = "username") String username,
            @FormParam(value = "password") String password,
            @FormParam(value = "canAccountLogin") @DefaultValue(value = "true") Boolean canAccountLogin,
            @FormParam(value = "canEmailLogin") @DefaultValue(value = "true") Boolean canEmailLogin,
            @FormParam(value = "canMobileLogin") @DefaultValue(value = "true") Boolean canMobileLogin) {

        String succLoginAccountId = tcAccountService.canSignin(
                username, password, canAccountLogin, canEmailLogin, canMobileLogin);

        // TODO mq

        return TcR.ok(succLoginAccountId);
    }

    @Override
    public TcP<List<TmPermission>, Void> findMenus(String accountId) {
        List<TmPermission> menuList = tcAuthService.findMenus(accountId);
        return TcP.ok(menuList);
    }

    @Override
    public TcP<List<TmPermission>, Void> findPms(String accountId) {
        List<TmPermission> pms = tcAuthService.findPms(accountId);
        return TcP.ok(pms);
    }

}
