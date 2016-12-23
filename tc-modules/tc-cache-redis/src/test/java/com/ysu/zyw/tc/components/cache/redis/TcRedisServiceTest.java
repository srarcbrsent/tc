package com.ysu.zyw.tc.components.cache.redis;

import com.fasterxml.jackson.core.type.TypeReference;
import com.ysu.zyw.tc.base.constant.TcConstant;
import com.ysu.zyw.tc.base.tools.TcIdGen;
import com.ysu.zyw.tc.base.utils.TcUtils;
import com.ysu.zyw.tc.components.cache.api.TcCacheService;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "classpath*:/config/tc-import-cache-redis.xml",
        "classpath*:/config/tc-spring-cache-redis.xml"
})
@Slf4j
public class TcRedisServiceTest {

    @Resource
    private ApplicationContext applicationContext;

    private TcCacheService tcCacheService;

    private static final int THREADS = 30;

    private static final int LOOPS = 5000;

    private static final int DELAY = 50;

    private ExecutorService executorService;

    @Before
    public void setUp() throws Exception {
        tcCacheService = applicationContext.getBean(TcConstant.BeanNames.SS_REDIS_CACHE, TcCacheService.class);
        executorService = Executors.newFixedThreadPool(THREADS);
    }

    @After
    public void tearDown() throws Exception {
        executorService.shutdown();
    }

    @Test
    public void allInOne() {
        tcCacheService = applicationContext.getBean(TcConstant.BeanNames.SS_REDIS_CACHE, TcCacheService.class);
        log.info("---------- SS_REDIS_CACHE_START");
        loop();
        tcCacheService = applicationContext.getBean(TcConstant.BeanNames.SO_CODIS_CACHE, TcCacheService.class);
        log.info("---------- SO_REDIS_CACHE_START");
        loop();
        tcCacheService = applicationContext.getBean(TcConstant.BeanNames.OO_CODIS_CACHE, TcCacheService.class);
        log.info("---------- OO_REDIS_CACHE_START");
        loop();
    }

    private void loop() {
        test_Cache_1();
    }

    @Test
    public void test_Cache_1() {
        doWith(() -> {
            String key = TcIdGen.upperCaseUuid();
            String value = TcIdGen.upperCaseUuid();
            tcCacheService.set(key, value, 60000);
            String sValue = tcCacheService.get(key, new TypeReference<String>() {
            });
            Assert.assertEquals(value, sValue);
        }, "simple [{}]");
    }

    private void doWith(TcUtils.TcTask tcTask, String expression) {
        TcUtils.doWithTiming(() -> {
            IntStream.range(0, THREADS).forEach(i -> {
                executorService.submit(() -> {
                    IntStream.range(0, LOOPS).forEach(j -> {
                        tcTask.execute();
                    });
                });
            });
        }, expression);
    }

}
