package com.ysu.zyw.tc.api.impl.accounts.auth;

import com.google.common.base.Preconditions;
import com.ysu.zyw.tc.api.api.TcAuthenticationApi;
import com.ysu.zyw.tc.api.fk.ex.TcVerifyFailureException;
import com.ysu.zyw.tc.api.svc.accounts.TcAccountService;
import com.ysu.zyw.tc.api.svc.accounts.auth.TcAuthService;
import com.ysu.zyw.tc.model.api.accounts.TmAccount;
import com.ysu.zyw.tc.model.api.accounts.auth.TmPermission;
import com.ysu.zyw.tc.model.mw.TcP;
import com.ysu.zyw.tc.model.mw.TcR;
import com.ysu.zyw.tc.model.validator.TcValidator;

import javax.annotation.Resource;
import java.util.List;

public class TcAuthenticationApiImpl implements TcAuthenticationApi {

    @Resource
    private TcAuthService tcAuthService;

    @Resource
    private TcAccountService tcAccountService;

    @Override
    public TcR<TmAccount, TcValidator.TcVerifyFailure> signup(
            String username,
            String password,
            Boolean canAccountLogin,
            Boolean canEmailLogin,
            Boolean canMobileLogin) {

        String succLoginAccountId;
        try {
            succLoginAccountId = tcAccountService.canSignup(
                    username, password, canAccountLogin, canEmailLogin, canMobileLogin);
        } catch (TcVerifyFailureException e) {
            // signup failed
            TcValidator.TcVerifyFailure tcVerifyFailure = e.getTcVerifyFailure();
            return new TcR<>(TcR.R.UNPROCESSABLE_ENTITY, TcR.R.UNPROCESSABLE_ENTITY_DESCRIPTION, null, tcVerifyFailure);
        }

        Preconditions.checkNotNull(succLoginAccountId);
        TmAccount tmAccount = tcAccountService.findAccount(succLoginAccountId, false, false);

        // TODO mq

        return TcR.ok(tmAccount);
    }

    @Override
    public TcP<List<TmPermission>, Void> findMenus(String accountId) {
        List<TmPermission> menuList = tcAuthService.fetchMenus(accountId);
        return TcP.ok(menuList);
    }

    @Override
    public TcP<List<TmPermission>, Void> findPms(String accountId) {
        List<TmPermission> pms = tcAuthService.findPms(accountId);
        return TcP.ok(pms);
    }

}
