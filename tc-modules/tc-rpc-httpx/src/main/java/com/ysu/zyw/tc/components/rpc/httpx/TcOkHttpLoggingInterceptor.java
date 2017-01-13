package com.ysu.zyw.tc.components.rpc.httpx;

import com.ysu.zyw.tc.base.tools.TcIdGen;
import com.ysu.zyw.tc.base.utils.TcDateUtils;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.Date;

@Slf4j
public class TcOkHttpLoggingInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Date before = new Date();
        String reqId = TcIdGen.upperCaseUuid().substring(0, 16);
        Request request = chain.request();

        log.info("start call http api [{}:{}] request id [{}] request body [{}]",
                request.method(), request.url(), reqId, request.body());

        Response response = chain.proceed(request);

        long duration = TcDateUtils.duration(before, new Date());
        log.info("end call http api [{}:{}] request id [{}] status code [{}] response body [{}] take time [{}]",
                request.method(), request.url(), reqId, response.code(), response.body(), duration);

        return response;
    }

}
