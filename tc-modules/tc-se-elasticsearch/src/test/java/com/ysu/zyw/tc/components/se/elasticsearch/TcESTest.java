package com.ysu.zyw.tc.components.se.elasticsearch;

import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.client.transport.TransportClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.net.UnknownHostException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "classpath*:/config/tc-import-se-elasticsearch.xml",
        "classpath*:/config/tc-spring-se-elasticsearch.xml"
})
@Slf4j
public class TcESTest {

    @Resource
    private TransportClient client;

    @Test
    public void test() throws UnknownHostException {
        client.listedNodes().forEach(System.out::println);
    }

}
