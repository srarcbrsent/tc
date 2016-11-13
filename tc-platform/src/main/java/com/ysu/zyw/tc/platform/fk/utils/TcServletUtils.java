package com.ysu.zyw.tc.platform.fk.utils;

import javax.servlet.http.HttpServletRequest;

public class TcServletUtils {

    public String extractIp(HttpServletRequest request) {
        return request.getRemoteAddr();
    }

}
