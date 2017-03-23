package com.ysu.zyw.tc.openapi.fk.shiro.filter;

import org.apache.shiro.authz.annotation.Logical;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class TcRequiresAllRolesDynamicFilter extends TcAbstractRequiresRolesDynamicFilter {

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue)
            throws Exception {
        return tcRequiresRolesDynamicSup.isAccessAllowed((String[]) mappedValue, Logical.AND);
    }

}
