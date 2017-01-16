package com.ysu.zyw.tc.openapi.fk.shiro.support;

import com.google.common.collect.Lists;
import com.ysu.zyw.tc.api.api.accounts.TcAuthenticationApi;
import com.ysu.zyw.tc.model.api.o.accounts.auth.ToRole;
import com.ysu.zyw.tc.model.mw.TcP;
import com.ysu.zyw.tc.openapi.constant.TcSessionKey;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.subject.Subject;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.google.common.base.Preconditions.checkNotNull;

public class TcRequiresRolesDynamicSup {

    @Resource
    private TcAuthenticationApi tcAuthenticationApi;

    public boolean isAccessAllowed(String[] roles, Logical logical) {
        Subject subject = SecurityUtils.getSubject();
        if (!subject.isAuthenticated() && !subject.isRemembered()) {
            return false;
        }

        String accountId = subject.getSession().getAttribute(TcSessionKey.S_ACCOUNT_ID).toString();
        checkNotNull(accountId);
        TcP<List<ToRole>> tcP = tcAuthenticationApi.findRoles(accountId);
        List<String> actualRoles = tcP.orElseGet(Lists::newArrayList).stream()
                .map(ToRole::getId)
                .collect(Collectors.toList());
        ArrayList<String> expectRoles = Lists.newArrayList(roles);

        if (Logical.AND.equals(logical)) {
            // contains all in expect roles.
            int expectRolesSize = expectRoles.size();
            return CollectionUtils.retainAll(expectRoles, actualRoles).size() == expectRolesSize;
        } else {
            // contains in both roles.
            return CollectionUtils.retainAll(expectRoles, actualRoles).size() != 0;
        }
    }

}
