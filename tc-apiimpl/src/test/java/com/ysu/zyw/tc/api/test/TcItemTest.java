package com.ysu.zyw.tc.api.test;

import com.ysu.zyw.tc.api.dao.mappers.TcItemMapper;
import com.ysu.zyw.tc.api.svc.items.TcItemService;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.client.transport.TransportClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "classpath*:/config/tc-spring-deploy.xml",
        "classpath*:/config/tc-spring-cache-redis.xml",
        "classpath*:/config/tc-spring-se-elasticsearch.xml"
})
@Slf4j
public class TcItemTest {

    @Resource
    private TcItemService tcItemService;

    @Resource
    private TcItemMapper tcItemMapper;

    @Resource
    private TransportClient transportClient;

    @Test
    public void testBySpringDataES() {
    }

}
