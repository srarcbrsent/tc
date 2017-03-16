package com.ysu.zyw.tc.openapi.fk.shiro;

import com.google.common.collect.Lists;
import com.ysu.zyw.tc.api.api.accounts.TcAuthenticationApi;
import com.ysu.zyw.tc.model.api.i.accounts.TiSignupTerms;
import com.ysu.zyw.tc.model.api.o.accounts.ToAccount;
import com.ysu.zyw.tc.model.api.o.accounts.auth.ToPermission;
import com.ysu.zyw.tc.model.api.o.accounts.auth.ToRole;
import com.ysu.zyw.tc.model.mw.TcP;
import com.ysu.zyw.tc.model.mw.TcR;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

public class TcAuthorizingRealm extends AuthorizingRealm {

    @Resource
    private TcAuthenticationApi tcAuthenticationApi;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        checkNotNull(principals.getPrimaryPrincipal());
        checkArgument(principals.getPrimaryPrincipal() instanceof ToAccount);

        ToAccount toAccount = (ToAccount) principals.getPrimaryPrincipal();

        List<ToRole> toRoles = fetchRoles(toAccount.getId());
        List<ToPermission> toPermissions = fetchPermissions(toAccount.getId());

        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        authorizationInfo.addRoles(
                toRoles.stream()
                        .map(ToRole::getId)
                        .collect(Collectors.toList())
        );

        authorizationInfo.addStringPermissions(
                toPermissions.parallelStream()
                        .map(ToPermission::getId)
                        .collect(Collectors.toList())
        );

        return authorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token)
            throws AuthenticationException {
        checkArgument(token instanceof UsernamePasswordToken,
                "this realm is only support username password token");

        String uUsername = ((UsernamePasswordToken) token).getUsername();
        checkArgument(uUsername.length() > 0);
        // this is => String uPassword = String.valueOf(((UsernamePasswordToken) token).getPassword());

        ToAccount toAccount = fetchAccount(uUsername);

        if (Objects.isNull(toAccount)) {
            throw new UnknownAccountException();
        }

        String dbPassword = toAccount.getPassword();
        // tcAccount as principal, so remove password.
        toAccount.setPassword(null);

        return new SimpleAuthenticationInfo(toAccount, dbPassword, fetchRealmName());
    }

    protected ToAccount fetchAccount(String username) {
        TcR<ToAccount> tcR = tcAuthenticationApi.signup(
                new TiSignupTerms(username, true, true));

        if (tcR.isPresent()) {
            return tcR.get();
        }

        int code = tcR.getCode();

        // code == 1 => 账号不存在;
        if (Objects.equals(code, 1)) {
            throw new UnknownAccountException("账号不存在！");
        }
        // code == 2 => 账号被锁定;
        if (Objects.equals(code, 2)) {
            throw new LockedAccountException("账号被锁定！");
        }

        // Unreachable
        throw new IllegalStateException("系统异常！");
    }

    protected List<ToRole> fetchRoles(String accountId) {
        TcP<List<ToRole>> tcP = tcAuthenticationApi.findRoles(accountId);
        return tcP.orElseGet(Lists::newArrayList);
    }

    protected List<ToPermission> fetchPermissions(String accountId) {
        // no api permissions have been set, all api privilege was set by role.
        return Lists.newArrayList();
    }

    protected String fetchRealmName() {
        return "default";
    }

}
