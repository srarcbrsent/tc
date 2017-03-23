package com.ysu.zyw.tc.openapi.fk.shiro.filter;

import com.ysu.zyw.tc.components.servlet.support.TcServletUtils;
import com.ysu.zyw.tc.components.servlet.support.TcXsrfTokenFilter;
import com.ysu.zyw.tc.model.mw.Rc;
import com.ysu.zyw.tc.model.mw.TcR;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

@Slf4j
public class TcFormAuthenticationFilter extends FormAuthenticationFilter {

    @Override
    protected void redirectToLogin(ServletRequest request, ServletResponse response) throws IOException {
        if (TcServletUtils.isXmlHttpRequest(WebUtils.toHttp(request))) {
            TcR<?> tcR = TcR.code(Rc.UNAUTHORIZED, Rc.UNAUTHORIZED_DESCRIPTION);
            TcServletUtils.writeApplicationJsonResponse(WebUtils.toHttp(response), tcR);
            log.warn("path [{}] authc failed, redirect to login", WebUtils.toHttp(request).getRequestURI());
        } else {
            TcXsrfTokenFilter.removeXsrfCookie(WebUtils.toHttp(response));
            super.redirectToLogin(request, response);
        }
    }

}
