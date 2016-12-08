package com.ysu.zyw.tc.platform.fk.shiro.filter;

import com.google.common.collect.Lists;
import com.ysu.zyw.tc.api.api.TcAuthenticationApi;
import com.ysu.zyw.tc.base.constant.TcConstant;
import com.ysu.zyw.tc.base.ex.TcException;
import com.ysu.zyw.tc.model.api.o.accounts.auth.ToRole;
import com.ysu.zyw.tc.model.mw.TcP;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.google.common.base.Preconditions.checkNotNull;

public class TcRequiresRolesDynamiclyFilter extends AccessControlFilter {

    @Resource
    private TcAuthenticationApi tcAuthenticationApi;

    @Override
    protected boolean isAccessAllowed(ServletRequest request,
                                      ServletResponse response,
                                      Object mappedValue) throws Exception {
        Subject subject = getSubject(request, response);
        if (!subject.isAuthenticated() && !subject.isRemembered()) {
            return true;
        }

        String accountId = subject.getSession().getAttribute(TcConstant.S.SESSION_ACCOUNT_ID).toString();
        checkNotNull(accountId);
        return isAccessAllowed(accountId, (String[]) mappedValue);
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request,
                                     ServletResponse response) throws Exception {
        saveRequestAndRedirectToLogin(request, response);
        return true;
    }

    private boolean isAccessAllowed(String accountId, String[] rolesRequired) {
        if (rolesRequired == null) {
            throw new TcException("must set the dynamic roles");
        }
        TcP<List<ToRole>> tcP = tcAuthenticationApi.findRoles(accountId);
        List<String> roles = tcP.orElseGet(Lists::newArrayList).stream()
                .map(ToRole::getId)
                .collect(Collectors.toList());
        ArrayList<String> rolesRequiredList = Lists.newArrayList(rolesRequired);
        int rolesRequiredLength = rolesRequiredList.size();
        rolesRequiredList.retainAll(roles);
        return rolesRequiredList.size() == rolesRequiredLength;
    }

}
