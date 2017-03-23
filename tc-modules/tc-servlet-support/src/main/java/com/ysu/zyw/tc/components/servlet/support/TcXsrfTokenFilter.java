package com.ysu.zyw.tc.components.servlet.support;


import com.ysu.zyw.tc.base.constant.TcBaseConsts;
import com.ysu.zyw.tc.model.mw.Rc;
import com.ysu.zyw.tc.model.mw.TcR;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Slf4j
public class TcXsrfTokenFilter extends OncePerRequestFilter {

    public static final String XSRF_TOKEN_COOKIE_NAME = "XSRF-TOKEN";

    private static final String XSRF_TOKEN_HEADER_NAME = "X-XSRF-TOKEN";

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        // only xml http request check xsrf token
        if (TcServletUtils.isXmlHttpRequest(request) && Objects.nonNull(request.getCookies())) {
            Optional<Cookie> cookieOptional = Arrays.stream(request.getCookies())
                    .filter(cookie -> Objects.equals(cookie.getName(), XSRF_TOKEN_COOKIE_NAME))
                    .findAny();
            // no cookie, yes header -> impossible
            // yes cookie, no header -> not a correct req
            // no cookie, no header -> pass
            // yes cookie, yes header -> go to verify
            if (cookieOptional.isPresent()) {
                Cookie cookie = cookieOptional.get();
                String header = request.getHeader(XSRF_TOKEN_HEADER_NAME);
                if (Objects.isNull(header)) {
                    log.warn("req [{}] has xsrf cookie but do not pass a xsrf token in xml http req, not a valid req",
                            request.getRequestURI());
                    writeForbiddenResponse(response);
                    return;
                }
                if (!Objects.equals(cookie.getValue(), header)) {
                    log.warn("req [{}] has xsrf cookie and xsrf token but they are not match", request.getRequestURI());
                    writeForbiddenResponse(response);
                    return;
                }
            }
        }
        filterChain.doFilter(request, response);
    }

    protected void writeForbiddenResponse(HttpServletResponse response) {
        TcR<?> tcR = TcR.code(Rc.UNAUTHORIZED, Rc.XSRF_TOKEN_NOT_MATCH);
        TcServletUtils.writeApplicationJsonResponse(response, tcR);
    }

    public static void addXsrfCookie(HttpServletResponse response) {
        Cookie xsrfCookie = new Cookie(TcXsrfTokenFilter.XSRF_TOKEN_COOKIE_NAME, UUID.randomUUID().toString());
        xsrfCookie.setDomain(TcBaseConsts.PROJECT_TC_DOMAIN);
        response.addCookie(xsrfCookie);
    }

    public static void removeXsrfCookie(HttpServletResponse response) {
        Cookie xsrfCookie = new Cookie(TcXsrfTokenFilter.XSRF_TOKEN_COOKIE_NAME, null);
        xsrfCookie.setDomain(TcBaseConsts.PROJECT_TC_DOMAIN);
        response.addCookie(xsrfCookie);
    }

}
