package com.ysu.zyw.tc.openapi.fk.shiro.enhance;

import com.ysu.zyw.tc.openapi.fk.shiro.support.TcRequiresRolesDynamicSup;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.web.filter.AccessControlFilter;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class TcRequiresRolesDynamicFilter extends AccessControlFilter {

    @Resource
    private TcRequiresRolesDynamicSup tcRequiresRolesDynamicSup;

    @Override
    protected boolean isAccessAllowed(ServletRequest request,
                                      ServletResponse response,
                                      Object mappedValue) throws Exception {
        return tcRequiresRolesDynamicSup.isAccessAllowed((String[]) mappedValue, Logical.AND);
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request,
                                     ServletResponse response) throws Exception {
        saveRequestAndRedirectToLogin(request, response);
        return true;
    }

}
