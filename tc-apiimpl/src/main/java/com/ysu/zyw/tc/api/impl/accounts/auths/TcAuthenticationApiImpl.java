package com.ysu.zyw.tc.api.impl.accounts.auths;

import com.ysu.zyw.tc.api.api.accounts.TcAuthenticationApi;
import com.ysu.zyw.tc.api.svc.accounts.TcAccountService;
import com.ysu.zyw.tc.api.svc.accounts.auth.TcAuthService;
import com.ysu.zyw.tc.model.api.i.accounts.TiLoginTerms;
import com.ysu.zyw.tc.model.api.o.accounts.ToAccount;
import com.ysu.zyw.tc.model.api.o.accounts.auth.ToMenu;
import com.ysu.zyw.tc.model.api.o.accounts.auth.ToPermission;
import com.ysu.zyw.tc.model.api.o.accounts.auth.ToRole;
import com.ysu.zyw.tc.model.mw.TcP;
import com.ysu.zyw.tc.model.mw.TcR;
import lombok.SneakyThrows;
import org.apache.commons.lang3.BooleanUtils;

import javax.annotation.Resource;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public class TcAuthenticationApiImpl implements TcAuthenticationApi {

    @Resource
    private TcAuthService tcAuthService;

    @Resource
    private TcAccountService tcAccountService;

    @SneakyThrows
    @Override
    public TcR<ToAccount> login(TiLoginTerms tiLoginTerms) {

        String succLoginAccountId = tcAccountService.login(
                tiLoginTerms.getUsername(),
                BooleanUtils.isTrue(tiLoginTerms.getCanEmailLogin()),
                BooleanUtils.isTrue(tiLoginTerms.getCanEmailLogin())
        );

        checkNotNull(succLoginAccountId);
        ToAccount toAccount = tcAccountService.findAccount(succLoginAccountId, true);

        return TcR.ok(toAccount);
    }

    @Override
    public TcP<List<ToMenu>> findMenus(String accountId) {

        List<ToMenu> toMenus = tcAuthService.fetchMenus(accountId);

        return TcP.ok(toMenus);
    }

    @Override
    public TcP<List<ToRole>> findRoles(String accountId) {

        List<ToRole> toRoles = tcAuthService.fetchRoleList(accountId);

        return TcP.ok(toRoles);
    }

    @Override
    public TcP<List<ToPermission>> findPermissions(String accountId) {

        List<ToPermission> toPermissions = tcAuthService.fetchPermissions(accountId);

        return TcP.ok(toPermissions);
    }

}
