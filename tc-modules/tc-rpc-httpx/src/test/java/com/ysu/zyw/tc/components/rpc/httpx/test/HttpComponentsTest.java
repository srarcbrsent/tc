package com.ysu.zyw.tc.components.rpc.httpx.test;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import java.io.IOException;

public class HttpComponentsTest {


    @Test
    public void testHttpComponents() throws IOException {
        HttpGet httpget = new HttpGet("http://www.baidu.com");
        try (CloseableHttpClient httpclient = HttpClients.createDefault();
             CloseableHttpResponse response = httpclient.execute(httpget)) {
            System.out.printf("内容类型为：%s", response.getEntity().getContentType());
            System.out.println(EntityUtils.toString(response.getEntity()));
        }
    }

}
