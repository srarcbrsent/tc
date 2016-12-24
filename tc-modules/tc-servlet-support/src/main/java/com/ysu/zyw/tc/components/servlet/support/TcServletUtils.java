package com.ysu.zyw.tc.components.servlet.support;

import lombok.experimental.UtilityClass;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

@UtilityClass
public class TcServletUtils {

    private static final String X_REAL_IP_HEADER = "X-Read-IP";

    private static final String X_REQUESTED_WITH_HEADER = "X-Requested-With";

    private static final String IP_REGEXP =
            "^(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])"
                    + "\\.(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])"
                    + "\\.(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])"
                    + "\\.(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])$";

    public static String extractIp(HttpServletRequest request) {
        if (isIp(request.getHeader(X_REAL_IP_HEADER))) {
            return request.getHeader(X_REAL_IP_HEADER);
        }
        if (isIp(request.getRemoteAddr())) {
            return request.getRemoteAddr();
        }
        return "0.0.0.0";
    }

    private static boolean isIp(String ip) {
        return Objects.nonNull(ip) && ip.matches(IP_REGEXP);
    }

    public static boolean isXmlHttpRequest(HttpServletRequest request) {
        return Objects.equals(request.getHeader(X_REQUESTED_WITH_HEADER), "XMLHttpRequest");
    }

}
