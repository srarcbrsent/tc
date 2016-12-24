package com.ysu.zyw.tc.components.servlet.support;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
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

@Slf4j
public class TcXsrfTokenFilter extends OncePerRequestFilter {

    public static final String XSRF_TOKEN_COOKIE_NAME = "XSRF-TOKEN";

    private static final String XSRF_TOKEN_HEADER_NAME = "X-XSRF-TOKEN";

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        // only xml http request check xsrf token
        if (TcServletUtils.isXmlHttpRequest(request)) {
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
                    response.sendError(HttpStatus.FORBIDDEN.value(), "拒绝服务");
                    return;
                }
                if (!Objects.equals(cookie.getValue(), header)) {
                    log.warn("req [{}] has xsrf cookie and xsrf token but they are not match", request.getRequestURI());
                    response.sendError(HttpStatus.FORBIDDEN.value(), "拒绝服务");
                    return;
                }
            }
        }
        filterChain.doFilter(request, response);
    }

}
