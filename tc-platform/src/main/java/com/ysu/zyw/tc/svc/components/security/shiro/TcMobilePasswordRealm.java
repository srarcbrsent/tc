package com.ysu.zyw.tc.svc.components.security.shiro;

import com.google.common.base.Preconditions;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

public class TcMobilePasswordRealm extends AuthorizingRealm {

    private static final String REALM_NAME = "JDBC_USERNAME_REALM";

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        Preconditions.checkNotNull(principals.getPrimaryPrincipal());

        return new SimpleAuthorizationInfo();
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        Preconditions.checkArgument(token instanceof UsernamePasswordToken,
                "this realm is only support username password token");

        String username = ((UsernamePasswordToken) token).getUsername();

        String password = String.valueOf(((UsernamePasswordToken) token).getPassword());

        // this place only select by username, password's verify is credentials matcher.


//            throw new UnknownAccountException("unknown account '" + username + "'");


//            throw new LockedAccountException("locked account '" + username + "'");

        // first argument is principal
        return new SimpleAuthenticationInfo(null, null, REALM_NAME);
    }

}