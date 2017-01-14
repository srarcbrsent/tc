package com.ysu.zyw.tc.components.rpc.httpx;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import okhttp3.ConnectionPool;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.FactoryBean;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.google.common.base.Preconditions.checkArgument;


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

    @Getter
    @Setter
    private int maxIdleConnections = 5;

    @Getter
    @Setter
    private long keepAliveDuration = 5 * 60 * 1000;

    @Override
    public OkHttpClient getObject() throws Exception {
        checkArgument(pingInterval > 0);
        checkArgument(connectTimeout > 0);
        checkArgument(writeTimeout > 0);
        checkArgument(readTimeout > 0);
        checkArgument(maxIdleConnections > 0);
        checkArgument(keepAliveDuration > 0);
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder
                .pingInterval(pingInterval, TimeUnit.MILLISECONDS)
                .connectTimeout(connectTimeout, TimeUnit.MILLISECONDS)
                .writeTimeout(writeTimeout, TimeUnit.MILLISECONDS)
                .readTimeout(readTimeout, TimeUnit.MILLISECONDS)
                .connectionPool(new ConnectionPool(maxIdleConnections, keepAliveDuration, TimeUnit.MILLISECONDS));
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
