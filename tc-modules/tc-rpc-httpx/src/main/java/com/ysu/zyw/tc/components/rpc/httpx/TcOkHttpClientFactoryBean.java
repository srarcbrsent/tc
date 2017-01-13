package com.ysu.zyw.tc.components.rpc.httpx;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.FactoryBean;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
public class TcOkHttpClientFactoryBean implements FactoryBean<OkHttpClient> {

    @Getter
    @Setter
    private long pingInterval = 900_000;

    @Getter
    @Setter
    private long connectTimeout = 10_000;

    @Getter
    @Setter
    private long writeTimeout = 10_000;

    @Getter
    @Setter
    private long readTimeout = 10_000;

    @Getter
    @Setter
    private List<Interceptor> interceptors;

    @Override
    public OkHttpClient getObject() throws Exception {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder
                .pingInterval(pingInterval, TimeUnit.MILLISECONDS)
                .connectTimeout(connectTimeout, TimeUnit.MILLISECONDS)
                .writeTimeout(writeTimeout, TimeUnit.MILLISECONDS)
                .readTimeout(readTimeout, TimeUnit.MILLISECONDS);
        if (CollectionUtils.isNotEmpty(interceptors)) {
            interceptors.forEach(builder::addInterceptor);
        }
        OkHttpClient okHttpClient = builder.build();
        log.info("successful instance ok http client ...");
        return okHttpClient;
    }

    @Override
    public Class<?> getObjectType() {
        return OkHttpClient.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

}
