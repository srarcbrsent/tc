package com.ysu.zyw.tc.components.servlet.support;

import com.ysu.zyw.tc.base.utils.TcSerializationUtils;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

import javax.annotation.Nonnull;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

import static com.google.common.base.Preconditions.checkNotNull;

@UtilityClass
public class TcServletUtils {

    private static final String X_REAL_IP_HEADER = "X-Read-IP";

    private static final String X_REQUESTED_WITH_HEADER = "X-Requested-With";

    private static final String CONTENT_TYPE_APPLICATION_JSON = "application/json; charset=utf-8";

    private static final String IP_REGEXP =
            "^(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])"
                    + "\\.(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])"
                    + "\\.(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])"
                    + "\\.(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])$";

    public static String extractIp(@Nonnull HttpServletRequest request) {
        checkNotNull(request);
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

    public static boolean isXmlHttpRequest(@Nonnull HttpServletRequest request) {
        checkNotNull(request);
        return Objects.equals(request.getHeader(X_REQUESTED_WITH_HEADER), "XMLHttpRequest");
    }

    @SneakyThrows
    public static void writeApplicationJsonResponse(@Nonnull HttpServletResponse response, @Nonnull Object value) {
        checkNotNull(response);
        checkNotNull(value);
        String json = TcSerializationUtils.writeJson(value);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setContentType(CONTENT_TYPE_APPLICATION_JSON);
        try (PrintWriter writer = response.getWriter()) {
            writer.write(json);
            writer.flush();
        }
    }

}
