package com.ysu.zyw.tc.svc.components.security.shiro;

import com.google.common.collect.Lists;
import com.ysu.zyw.tc.dao.penum.TcPermissionType;
import com.ysu.zyw.tc.dao.po.TcAccount;
import com.ysu.zyw.tc.dao.po.TcPermission;
import com.ysu.zyw.tc.dao.po.TcPermissionSet;
import com.ysu.zyw.tc.svc.components.security.auth.TcAuthService;
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

public abstract class TcAbstractAuthorizingRealm extends AuthorizingRealm {

    @Resource
    private TcAuthService tcAuthService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        checkNotNull(principals.getPrimaryPrincipal());
        checkArgument(principals.getPrimaryPrincipal() instanceof TcAccount);

        TcAccount tcAccount = (TcAccount) principals.getPrimaryPrincipal();

        List<TcPermissionSet> tcPermissionSetList = tcAuthService.fetchPermissionSetList(tcAccount.getId());
        List<TcPermission> tcPermissionList =
                tcAuthService.fetchPermissionList(tcAccount.getId(), Lists.newArrayList(TcPermissionType.API));

        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        authorizationInfo.addRoles(
                tcPermissionSetList.stream()
                        .map(TcPermissionSet::getId)
                        .collect(Collectors.toList())
        );
        authorizationInfo.addStringPermissions(
                tcPermissionList.parallelStream()
                        .map(TcPermission::getId)
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


        TcAccount tcAccount = fetchAccount(uUsername);

        if (Objects.isNull(tcAccount)) {
            throw new UnknownAccountException();
        }

        String dbPassword = tcAccount.getPassword();
        // tcAccount as principal, so remove password.
        tcAccount.setPassword(null);

        // TODO throw new LockedAccountException("locked account '" + username + "'");

        return new SimpleAuthenticationInfo(tcAccount, dbPassword, fetchRealmName());
    }

    protected abstract TcAccount fetchAccount(String username);

    protected abstract String fetchRealmName();

}
