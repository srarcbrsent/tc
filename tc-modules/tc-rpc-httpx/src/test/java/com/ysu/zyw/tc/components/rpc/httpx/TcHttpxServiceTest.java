package com.ysu.zyw.tc.components.rpc.httpx;

import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.ResponseExtractor;

import javax.annotation.Resource;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "classpath*:/config/tc-import-rpc-httpx.xml",
        "classpath*:/config/tc-spring-rpc-httpx.xml"
})
@Slf4j
public class TcHttpxServiceTest {

    @Resource
    private TcHttpxService tcHttpxService;

    @Resource
    private OkHttpClient okHttpClient;

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testGetBaiduHomepage() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(35);
        for (int i = 0; i < 150; i++) {
            executorService.execute(() -> {
                tcHttpxService.getRestTemplate().execute("http://www.baidu.com", HttpMethod.GET,
                        null,
                        (ResponseExtractor<Void>) response -> null);
                System.out.println(okHttpClient.connectionPool().connectionCount());
            });
        }
        executorService.awaitTermination(3000, TimeUnit.MILLISECONDS);
    }

}