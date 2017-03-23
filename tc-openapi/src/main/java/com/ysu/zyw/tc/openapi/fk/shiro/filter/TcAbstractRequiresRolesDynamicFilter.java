package com.ysu.zyw.tc.openapi.fk.shiro.filter;

import com.ysu.zyw.tc.components.servlet.support.TcServletUtils;
import com.ysu.zyw.tc.components.servlet.support.TcXsrfTokenFilter;
import com.ysu.zyw.tc.model.mw.Rc;
import com.ysu.zyw.tc.model.mw.TcR;
import com.ysu.zyw.tc.openapi.fk.shiro.support.TcRequiresRolesDynamicSup;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

@Slf4j
public abstract class TcAbstractRequiresRolesDynamicFilter extends AccessControlFilter {

    @Resource
    protected TcRequiresRolesDynamicSup tcRequiresRolesDynamicSup;

    @Override
    protected boolean onAccessDenied(ServletRequest request,
                                     ServletResponse response) throws Exception {
        saveRequestAndRedirectToLogin(request, response);
        return false;
    }

    @Override
    protected void redirectToLogin(ServletRequest request, ServletResponse response) throws IOException {
        if (TcServletUtils.isXmlHttpRequest(WebUtils.toHttp(request))) {
            TcR<?> tcR = TcR.code(Rc.UNAUTHORIZED, Rc.UNAUTHORIZED_DESCRIPTION);
            log.warn("path [{}] requires role dynamic check failed, redirect to login",
                    WebUtils.toHttp(request).getRequestURI());
            TcServletUtils.writeApplicationJsonResponse(WebUtils.toHttp(response), tcR);
        } else {
            TcXsrfTokenFilter.removeXsrfCookie(WebUtils.toHttp(response));
            super.redirectToLogin(request, response);
        }
    }

}
