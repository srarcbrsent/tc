package com.ysu.zyw.tc.components.cache.redis;

import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.collect.Lists;
import com.ysu.zyw.tc.base.constant.TcConstant;
import com.ysu.zyw.tc.base.tools.TcIdGen;
import com.ysu.zyw.tc.base.utils.TcDateUtils;
import com.ysu.zyw.tc.base.utils.TcUtils;
import com.ysu.zyw.tc.components.cache.api.TcCacheService;
import com.ysu.zyw.tc.components.cache.api.TcOpsForGroupedValue;
import com.ysu.zyw.tc.components.cache.redis.model.TcCountryMdl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@SuppressWarnings("Duplicates")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "classpath*:/config/tc-import-cache-codis.xml",
        "classpath*:/config/tc-spring-cache-codis.xml"
})
@Slf4j
public class TcCodisServiceTest {

    @Resource
    private ApplicationContext applicationContext;

    private TcCacheService tcCacheService;

    private static final int THREADS = 50;

    private static int loops = 5000;

    private static final int DELAY = 500;

    private static final int CLUSTER_DELAY = 5000;

    private ExecutorService executorService;

    @Before
    public void setUp() throws Exception {
        tcCacheService = applicationContext.getBean(TcConstant.BeanNames.OO_CODIS_SERVICE, TcCacheService.class);
        executorService = Executors.newFixedThreadPool(THREADS);
    }

    @After
    public void tearDown() throws Exception {
        executorService.shutdown();
    }

    @Test
    public void allInOne() {
        tcCacheService = applicationContext.getBean(TcConstant.BeanNames.SS_CODIS_SERVICE, TcCacheService.class);
        log.info("---------- SS_REDIS_CACHE_START");
        loop();
        tcCacheService = applicationContext.getBean(TcConstant.BeanNames.SO_CODIS_SERVICE, TcCacheService.class);
        log.info("---------- SO_REDIS_CACHE_START");
        loop();
        tcCacheService = applicationContext.getBean(TcConstant.BeanNames.OO_CODIS_SERVICE, TcCacheService.class);
        log.info("---------- OO_REDIS_CACHE_START");
        loop();
    }

    private void loop() {
        test_Cache_1();
        test_Cache_2();
        test_Cache_3();
        test_Cache_4();
        test_Cache_5();
        test_Cache_6();
        test_GroupCache_1();
        test_GroupCache_2();
        test_GroupCache_3();
        test_GroupCache_4();
        test_GroupCache_5();
        test_GroupCache_6();
        test_GroupCache_7();
        test_GroupCache_8();
    }

    @Test(timeout = 30000L)
    public void test_Cache_1() {
        loops = 2000;
        doWith(() -> {
            String key = TcIdGen.upperCaseUuid();
            String value = TcIdGen.upperCaseUuid();
            tcCacheService.set(key, value, 60000);
            Assert.assertTrue(tcCacheService.exists(key));
            String sValue = tcCacheService.get(key, new TypeReference<String>() {
            });
            Assert.assertEquals(value, sValue);
        }, "simple [{}]");
    }

    @Test(timeout = 30000L)
    public void test_Cache_2() {
        loops = 50;
        doWith(() -> {
            String key = TcIdGen.upperCaseUuid();
            TcCountryMdl value = TcCountryMdl.newInstance();
            tcCacheService.set(key, value, 60000);
            Assert.assertTrue(tcCacheService.exists(key));
            TcCountryMdl sValue = tcCacheService.get(key, new TypeReference<TcCountryMdl>() {
            });
            Assert.assertEquals(value, sValue);
        }, "complex [{}]");
    }

    @Test(timeout = 9000L)
    public void test_Cache_3() {
        loops = 50;
        int timeout = 600;
        int oThrough = 10;
        AtomicInteger through = new AtomicInteger(oThrough);
        Date before = new Date();
        String key = TcIdGen.upperCaseUuid();
        TcCountryMdl value = TcCountryMdl.newInstance();
        tcCacheService.set(key, value, timeout);
        while (through.intValue() != 0) {
            TcCountryMdl sValue = tcCacheService.get(key, () -> {
                through.decrementAndGet();
                return value;
            }, timeout, null);
            Assert.assertEquals(value, sValue);
        }
        long duration = TcDateUtils.duration(before, new Date());
        Assert.assertTrue(duration < (timeout * oThrough + DELAY) &&
                duration > (timeout * oThrough - DELAY));
    }

    @Test(timeout = 6000L)
    public void test_Cache_4() {
        String key = TcIdGen.upperCaseUuid();
        TcCountryMdl value = TcCountryMdl.newInstance();
        ReentrantLock lock = new ReentrantLock();
        int oThrough = 10;
        AtomicInteger through = new AtomicInteger(oThrough);
        CountDownLatch countDownLatch = new CountDownLatch(THREADS);
        IntStream.range(0, THREADS).forEach(i -> {
            executorService.execute(() -> {
                TcCountryMdl sValue = tcCacheService.get(key, () -> {
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        log.error("", e);
                    }
                    through.decrementAndGet();
                    return value;
                }, 6000, lock);
                Assert.assertEquals(value, sValue);
                countDownLatch.countDown();
            });
        });
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            log.error("", e);
        }
        Assert.assertEquals(oThrough - 1, through.intValue());
    }

    @Test
    public void test_Cache_5() {
        String key = TcIdGen.upperCaseUuid();
        TcCountryMdl value = TcCountryMdl.newInstance();
        int timeout = 1500;
        tcCacheService.set(key, value, timeout);
        Assert.assertTrue(tcCacheService.exists(key));
        TcCountryMdl sValue = tcCacheService.get(key, new TypeReference<TcCountryMdl>() {
        });
        Assert.assertEquals(value, sValue);
        try {
            TimeUnit.MILLISECONDS.sleep(timeout);
        } catch (InterruptedException e) {
            log.error("", e);
        }
        Assert.assertFalse(tcCacheService.exists(key));
    }

    @Test
    public void test_Cache_6() {
        String key = TcIdGen.upperCaseUuid();
        TcCountryMdl value = TcCountryMdl.newInstance();
        int timeout = 1500;
        tcCacheService.set(key, value, timeout);
        Assert.assertTrue(tcCacheService.exists(key));
        try {
            TimeUnit.MILLISECONDS.sleep(1000);
        } catch (InterruptedException e) {
            log.error("", e);
        }
        tcCacheService.expire(key, timeout);
        try {
            TimeUnit.MILLISECONDS.sleep(1000);
        } catch (InterruptedException e) {
            log.error("", e);
        }
        Assert.assertTrue(tcCacheService.exists(key));
        try {
            TimeUnit.MILLISECONDS.sleep(500);
        } catch (InterruptedException e) {
            log.error("", e);
        }
        Assert.assertFalse(tcCacheService.exists(key));
    }

    @Test(timeout = 6000L)
    public void test_GroupCache_1() {
        TcOpsForGroupedValue tcOpsForGroupedValue = tcCacheService.opsForGroupedValue();
        loops = 2000;
        String group = TcIdGen.upperCaseUuid();
        doWith(() -> {
            String key = TcIdGen.upperCaseUuid();
            String value = TcIdGen.upperCaseUuid();
            tcOpsForGroupedValue.set(group, key, value, 60000);
            Assert.assertTrue(tcOpsForGroupedValue.exists(group, key));
            String sValue = tcOpsForGroupedValue.get(group, key, new TypeReference<String>() {
            });
            Assert.assertEquals(value, sValue);
        }, "simple [{}]");
    }

    @Test(timeout = 30000L)
    public void test_GroupCache_2() {
        TcOpsForGroupedValue tcOpsForGroupedValue = tcCacheService.opsForGroupedValue();
        loops = 50;
        String group = TcIdGen.upperCaseUuid();
        doWith(() -> {
            String key = TcIdGen.upperCaseUuid();
            TcCountryMdl value = TcCountryMdl.newInstance();
            tcOpsForGroupedValue.set(group, key, value, 60000);
            Assert.assertTrue(tcOpsForGroupedValue.exists(group, key));
            TcCountryMdl sValue = tcOpsForGroupedValue.get(group, key, new TypeReference<TcCountryMdl>() {
            });
            Assert.assertEquals(value, sValue);
        }, "complex [{}]");
    }

    @Test(timeout = 9000L)
    public void test_GroupCache_3() {
        TcOpsForGroupedValue tcOpsForGroupedValue = tcCacheService.opsForGroupedValue();
        loops = 50;
        int timeout = 600;
        int oThrough = 10;
        AtomicInteger through = new AtomicInteger(oThrough);
        Date before = new Date();
        String group = TcIdGen.upperCaseUuid();
        String key = TcIdGen.upperCaseUuid();
        TcCountryMdl value = TcCountryMdl.newInstance();
        tcOpsForGroupedValue.set(group, key, value, timeout);
        while (through.intValue() != 0) {
            TcCountryMdl sValue = tcOpsForGroupedValue.get(group, key, () -> {
                through.decrementAndGet();
                return value;
            }, timeout, null);
            Assert.assertEquals(value, sValue);
        }
        long duration = TcDateUtils.duration(before, new Date());
        Assert.assertTrue(duration < (timeout * oThrough + DELAY) &&
                duration > (timeout * oThrough - DELAY));
    }

    @Test(timeout = 6000L)
    public void test_GroupCache_4() {
        TcOpsForGroupedValue tcOpsForGroupedValue = tcCacheService.opsForGroupedValue();
        String group = TcIdGen.upperCaseUuid();
        String key = TcIdGen.upperCaseUuid();
        TcCountryMdl value = TcCountryMdl.newInstance();
        ReentrantLock lock = new ReentrantLock();
        int oThrough = 10;
        AtomicInteger through = new AtomicInteger(oThrough);
        CountDownLatch countDownLatch = new CountDownLatch(THREADS);
        IntStream.range(0, THREADS).forEach(i -> {
            executorService.execute(() -> {
                TcCountryMdl sValue = tcOpsForGroupedValue.get(group, key, () -> {
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        log.error("", e);
                    }
                    through.decrementAndGet();
                    return value;
                }, 6000, lock);
                Assert.assertEquals(value, sValue);
                countDownLatch.countDown();
            });
        });
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            log.error("", e);
        }
        Assert.assertEquals(oThrough - 1, through.intValue());
    }

    @Test
    public void test_GroupCache_5() {
        TcOpsForGroupedValue tcOpsForGroupedValue = tcCacheService.opsForGroupedValue();
        String group = TcIdGen.upperCaseUuid();
        String key = TcIdGen.upperCaseUuid();
        TcCountryMdl value = TcCountryMdl.newInstance();
        int timeout = 1500;
        tcOpsForGroupedValue.set(group, key, value, timeout);
        Assert.assertTrue(tcOpsForGroupedValue.exists(group, key));
        TcCountryMdl sValue = tcOpsForGroupedValue.get(group, key, new TypeReference<TcCountryMdl>() {
        });
        Assert.assertEquals(value, sValue);
        try {
            TimeUnit.MILLISECONDS.sleep(timeout + CLUSTER_DELAY);
        } catch (InterruptedException e) {
            log.error("", e);
        }
        Assert.assertFalse(tcOpsForGroupedValue.exists(group, key));
    }

    @Test
    public void test_GroupCache_6() {
        TcOpsForGroupedValue tcOpsForGroupedValue = tcCacheService.opsForGroupedValue();
        String group = TcIdGen.upperCaseUuid();
        String key = TcIdGen.upperCaseUuid();
        TcCountryMdl value = TcCountryMdl.newInstance();
        int timeout = 1500;
        tcOpsForGroupedValue.set(group, key, value, timeout);
        Assert.assertTrue(tcOpsForGroupedValue.exists(group, key));
        try {
            TimeUnit.MILLISECONDS.sleep(1000);
        } catch (InterruptedException e) {
            log.error("", e);
        }
        tcOpsForGroupedValue.expire(group, key, timeout);
        try {
            TimeUnit.MILLISECONDS.sleep(1000);
        } catch (InterruptedException e) {
            log.error("", e);
        }
        Assert.assertTrue(tcOpsForGroupedValue.exists(group, key));
        try {
            TimeUnit.MILLISECONDS.sleep(500);
        } catch (InterruptedException e) {
            log.error("", e);
        }
        Assert.assertFalse(tcOpsForGroupedValue.exists(group, key));
    }

    @Test
    public void test_GroupCache_7() {
        TcOpsForGroupedValue tcOpsForGroupedValue = tcCacheService.opsForGroupedValue();
        int timeout = 30000;
        List<String> ids = Lists.newArrayList();
        String group = TcIdGen.upperCaseUuid();
        IntStream.range(0, 200).forEach(i -> {
            String key = TcIdGen.upperCaseUuid();
            TcCountryMdl value = TcCountryMdl.newInstance();
            tcOpsForGroupedValue.set(group, key, value, timeout);
            ids.add(key);
        });

        Set<String> keys = tcOpsForGroupedValue.keys(group);
        Assert.assertEquals(0, CollectionUtils.removeAll(keys, ids).size());
    }

    @Test
    public void test_GroupCache_8() {
        TcOpsForGroupedValue tcOpsForGroupedValue = tcCacheService.opsForGroupedValue();
        int timeout = 30000;
        List<String> ids = Lists.newArrayList();
        String group = TcIdGen.upperCaseUuid();
        IntStream.range(0, 400).forEach(i -> {
            String key = TcIdGen.upperCaseUuid();
            TcCountryMdl value = TcCountryMdl.newInstance();
            tcOpsForGroupedValue.set(group, key, value, timeout);
            ids.add(key);
        });

        Set<String> delKeys = ids.stream().filter(id -> id.hashCode() % 2 == 0).collect(Collectors.toSet());
        Set<String> lftKeys = ids.stream().filter(id -> id.hashCode() % 2 != 0).collect(Collectors.toSet());

        delKeys.forEach(id -> tcOpsForGroupedValue.delete(group, id));

        Set<String> keys = tcOpsForGroupedValue.keys(group);
        Assert.assertEquals(lftKeys.size(), keys.size());


        Assert.assertEquals(0, CollectionUtils.removeAll(keys, lftKeys).size());

        tcOpsForGroupedValue.delete(group);

        Assert.assertEquals(0, tcOpsForGroupedValue.keys(group).size());
    }

    private void doWith(TcUtils.TcTask tcTask, String expression) {
        CountDownLatch countDownLatch = new CountDownLatch(THREADS);
        TcUtils.doWithTiming(() -> {
            IntStream.range(0, THREADS).forEach(i -> {
                executorService.execute(() -> {
                    IntStream.range(0, loops).forEach(j -> {
                        tcTask.execute();
                    });
                    countDownLatch.countDown();
                });
            });
            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                log.error("", e);
            }
        }, expression);
    }

}
