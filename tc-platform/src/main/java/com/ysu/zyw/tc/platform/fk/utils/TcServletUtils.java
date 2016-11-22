package com.ysu.zyw.tc.platform.fk.utils;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

public class TcServletUtils {

    private static final String X_REAL_IP_HEADER = "X-Read-IP";

    private static final String IP_REGEXP = "^(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])\\.(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])\\.(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])\\.(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])$";

    public String extractIp(HttpServletRequest request) {
        if (isIp(request.getHeader(X_REAL_IP_HEADER))) {
            return request.getHeader(X_REAL_IP_HEADER);
        }
        if (isIp(request.getRemoteAddr())) {
            return request.getRemoteAddr();
        }
        return "0.0.0.0";
    }

    private boolean isIp(String ip) {
        return Objects.nonNull(ip) && ip.matches(IP_REGEXP);
    }

}
