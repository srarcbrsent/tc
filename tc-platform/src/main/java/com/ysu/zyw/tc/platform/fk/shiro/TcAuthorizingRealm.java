package com.ysu.zyw.tc.platform.fk.shiro;

import com.google.common.collect.Lists;
import com.ysu.zyw.tc.api.api.TcAuthenticationApi;
import com.ysu.zyw.tc.model.api.o.accounts.ToAccount;
import com.ysu.zyw.tc.model.api.o.accounts.auth.ToPermission;
import com.ysu.zyw.tc.model.api.o.accounts.auth.ToRole;
import com.ysu.zyw.tc.model.mw.TcR;
import com.ysu.zyw.tc.model.validator.TcValidator;
import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

public class TcAuthorizingRealm extends AuthorizingRealm {

    @SuppressWarnings("SpringJavaAutowiringInspection")
    // @Resource
    private TcAuthenticationApi tcAuthenticationApi;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        checkNotNull(principals.getPrimaryPrincipal());
        checkArgument(principals.getPrimaryPrincipal() instanceof ToAccount);

        ToAccount toAccount = (ToAccount) principals.getPrimaryPrincipal();

        List<ToRole> toRoles = this.fetchRoles(toAccount.getId());
        List<ToPermission> toPermissions = this.fetchPermissions(toAccount.getId());

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
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        checkArgument(token instanceof UsernamePasswordToken,
                "this realm is only support username password token");

        String uUsername = ((UsernamePasswordToken) token).getUsername();
        checkArgument(uUsername.length() > 0);
        String uPassword = String.valueOf(((UsernamePasswordToken) token).getPassword());

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
        TcR<ToAccount, TcValidator.TcVerifyFailure> tcR = tcAuthenticationApi.signup(username, true, true, true);
        if (!tcR.isPresent()) {
            String msg = Objects.nonNull(tcR.getExtra()) && CollectionUtils.isNotEmpty(tcR.getExtra()) ? 
                    tcR.getExtra().get(0) : "系统异常！";
            throw new AuthenticationException(msg);
        }
        
        return tcR.get();
    }

    protected List<ToRole> fetchRoles(String accountId) {
        // TODO: 2016/11/12  
        return Lists.newArrayList();
    }

    protected List<ToPermission> fetchPermissions(String accountId) {
        // no api permissions have been set, all api privilege was set by role.
        return Lists.newArrayList();
    }

    protected String fetchRealmName() {
        return "default";
    }

}
