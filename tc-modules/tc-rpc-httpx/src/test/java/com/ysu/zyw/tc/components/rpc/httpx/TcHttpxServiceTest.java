package com.ysu.zyw.tc.components.rpc.httpx;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.ResponseExtractor;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "classpath*:/config/tc-import-rpc-httpx.xml",
        "classpath*:/config/tc-spring-rpc-httpx.xml"
})
@Slf4j
public class TcHttpxServiceTest {

    @Resource
    private TcHttpxService tcHttpxService;

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testGetBaiduHomepage() {
        tcHttpxService.getRestTemplate().execute("http://www.baidu.com", HttpMethod.GET,
                null,
                (ResponseExtractor<Void>) response -> {
                    System.out.println(IOUtils.toString(response.getBody(), StandardCharsets.UTF_8));
                    return null;
                });
    }

}