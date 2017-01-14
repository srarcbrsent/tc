package com.ysu.zyw.tc.components.rpc.httpx;

import com.ysu.zyw.tc.base.utils.TcDateUtils;
import com.ysu.zyw.tc.components.rpc.httpx.apis.TcStubController;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import org.apache.commons.lang3.RandomUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.ResponseExtractor;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Date;
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

    @Test
    public void testGetText4Entity() throws UnsupportedEncodingException {
        int id = RandomUtils.nextInt();
        Date now = new Date();
        TcStubController.TcStubs tcStubs = new TcStubController.TcStubs(id, now);
        MultiValueMap<String, String> multiValueMap = tcHttpxService.copy2MultiValueMap(tcStubs);
        Assert.assertEquals(String.valueOf(id), multiValueMap.getFirst("id"));
        Assert.assertEquals(TcDateUtils.format(now),
                URLDecoder.decode(multiValueMap.getFirst("now"), StandardCharsets.UTF_8.name()));
        ResponseEntity<TcStubController.TcStubs> response =
                tcHttpxService.getText4Entity("http://localhost:13006/getText",
                        null,
                        null,
                        multiValueMap,
                        new ParameterizedTypeReference<TcStubController.TcStubs>() {
                        });
        Assert.assertTrue(response.getStatusCode().is2xxSuccessful());
        Assert.assertEquals(tcStubs, response.getBody());
    }

    @Test
    public void testPostText4Entity() throws UnsupportedEncodingException {
        int id = RandomUtils.nextInt();
        Date now = new Date();
        TcStubController.TcStubs tcStubs = new TcStubController.TcStubs(id, now);
        MultiValueMap<String, String> multiValueMap = tcHttpxService.copy2MultiValueMap(tcStubs);
        Assert.assertEquals(String.valueOf(id), multiValueMap.getFirst("id"));
        Assert.assertEquals(TcDateUtils.format(now),
                URLDecoder.decode(multiValueMap.getFirst("now"), StandardCharsets.UTF_8.name()));
        ResponseEntity<TcStubController.TcStubs> response =
                tcHttpxService.postText4Entity("http://localhost:13006/postText",
                        null,
                        null,
                        multiValueMap,
                        new ParameterizedTypeReference<TcStubController.TcStubs>() {
                        });
        Assert.assertTrue(response.getStatusCode().is2xxSuccessful());
        Assert.assertEquals(tcStubs, response.getBody());
    }

    @Test
    public void testPostJson4Entity() throws UnsupportedEncodingException {
        int id = RandomUtils.nextInt();
        Date now = new Date();
        TcStubController.TcStubs tcStubs = new TcStubController.TcStubs(id, now);
        ResponseEntity<TcStubController.TcStubs> response =
                tcHttpxService.postJson4Entity("http://localhost:13006/postJson",
                        null,
                        null,
                        tcStubs,
                        new ParameterizedTypeReference<TcStubController.TcStubs>() {
                        });
        Assert.assertTrue(response.getStatusCode().is2xxSuccessful());
        Assert.assertEquals(tcStubs, response.getBody());
    }

}