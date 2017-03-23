package com.ysu.zyw.tc.openapi.fk.shiro;

import com.ysu.zyw.tc.components.servlet.support.TcServletUtils;
import com.ysu.zyw.tc.model.mw.Rc;
import com.ysu.zyw.tc.model.mw.TcR;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

public class TcFormAuthenticationFilter extends FormAuthenticationFilter {

    @Override
    protected void redirectToLogin(ServletRequest request, ServletResponse response) throws IOException {
        if (TcServletUtils.isXmlHttpRequest(WebUtils.toHttp(request))) {
            TcR<?> tcR = TcR.code(Rc.UNAUTHORIZED, Rc.UNAUTHORIZED_DESCRIPTION);
            TcServletUtils.writeApplicationJsonResponse(WebUtils.toHttp(response), tcR);
        } else {
            super.redirectToLogin(request, response);
        }
    }

}
