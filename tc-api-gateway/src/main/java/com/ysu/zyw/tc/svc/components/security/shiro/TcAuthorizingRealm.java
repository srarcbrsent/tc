package com.ysu.zyw.tc.svc.components.security.shiro;

import com.ysu.zyw.tc.components.httpx.TcHttpxService;
import com.ysu.zyw.tc.model.api.accounts.TmAccount;
import com.ysu.zyw.tc.model.api.accounts.auth.TmPermission;
import com.ysu.zyw.tc.model.api.accounts.auth.TmPermissionSet;
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
    private TcHttpxService tcHttpxService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        checkNotNull(principals.getPrimaryPrincipal());
        checkArgument(principals.getPrimaryPrincipal() instanceof TmAccount);

        TmAccount tmAccount = (TmAccount) principals.getPrimaryPrincipal();

        List<TmPermissionSet> tcPermissionSetList = this.fetchPermissionSetList(tmAccount.getId());
        List<TmPermission> tcPermissionList = this.fetchApiPermissionList(tmAccount.getId());

        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        authorizationInfo.addRoles(
                tcPermissionSetList.stream()
                        .map(TmPermissionSet::getId)
                        .collect(Collectors.toList())
        );
        authorizationInfo.addStringPermissions(
                tcPermissionList.parallelStream()
                        .map(TmPermission::getId)
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

        TmAccount tmAccount = fetchAccount(uUsername);

        if (Objects.isNull(tmAccount)) {
            throw new UnknownAccountException();
        }

        String dbPassword = tmAccount.getPassword();
        // tcAccount as principal, so remove password.
        tmAccount.setPassword(null);

        return new SimpleAuthenticationInfo(tmAccount, dbPassword, fetchRealmName());
    }

    protected TmAccount fetchAccount(String username) {
        // TODO
        return null;
    }

    protected List<TmPermissionSet> fetchPermissionSetList(String accountId) {
        // TODO
        return null;
    }


    protected List<TmPermission> fetchApiPermissionList(String accountId) {
        // TODO
        return null;
    }

    protected String fetchRealmName() {
        // TODO
        return null;
    }

}
