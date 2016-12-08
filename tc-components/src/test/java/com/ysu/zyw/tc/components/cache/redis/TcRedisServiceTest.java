package com.ysu.zyw.tc.components.cache.redis;

import com.ysu.zyw.tc.base.tools.TcIdGen;
import com.ysu.zyw.tc.components.cache.TcCacheService;
import com.ysu.zyw.tc.components.cache.codis.Mongo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "classpath*:config/tc-spring-cache-redis.xml",
        "classpath*:config/tc-spring-import-redis.xml"
})
public class TcRedisServiceTest {

    @Resource(name = "ssRedisService")
    private TcCacheService tcCacheService;

    @Test
    public void test01() {
        tcCacheService.set("2", new Mongo(TcIdGen.upperCaseUuid(), null), 20000);
        System.out.println(tcCacheService.get("2", Mongo.class));
    }

}
