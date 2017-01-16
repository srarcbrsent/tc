package com.ysu.zyw.tc.openapi.fk.shiro.enhance;

import com.ysu.zyw.tc.openapi.fk.shiro.support.TcRequiresRolesDynamicSup;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.web.filter.AccessControlFilter;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

@Slf4j
public abstract class TcAbstractRequiresRolesDynamicFilter extends AccessControlFilter {

    @Resource
    protected TcRequiresRolesDynamicSup tcRequiresRolesDynamicSup;

    @Override
    protected boolean onAccessDenied(ServletRequest request,
                                     ServletResponse response) throws Exception {
        saveRequestAndRedirectToLogin(request, response);
        log.info("reject request and redirect to login [{}]", request);
        return true;
    }

}
