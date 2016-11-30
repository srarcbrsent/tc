package com.ysu.zyw.tc.components.cache.codis;

import com.google.common.collect.Lists;
import com.ysu.zyw.tc.base.tools.TcIdWorker;
import com.ysu.zyw.tc.base.utils.TcDateUtils;
import com.ysu.zyw.tc.components.cache.TcCacheService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.math.RandomUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;
import java.util.stream.IntStream;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "classpath*:config/tc-spring-cache-codis.xml",
        "classpath*:config/tc-spring-import-codis.xml"
})
@Slf4j
public class TcCodisServiceTest {

    @Resource(name = "ssCodisService")
    private TcCacheService tcCacheService;

    private long invoke(Supplier<?> supplier) {
        Date now = new Date();
        supplier.get();
        return TcDateUtils.duration(now, new Date());
    }

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void set() throws Exception {
        final int loop = 50000;
        final long duration = 20000;

        log.info("start ...");

        long simpleStrDuration = invoke(() -> {
            IntStream.range(0, 30 * loop).parallel().forEach(i -> {
                String uuid = TcIdWorker.upperCaseUuid();
                String value = TcIdWorker.upperCaseUuid();
                tcCacheService.set(uuid, value, duration);
                String sValue = tcCacheService.get(uuid, String.class);
                Assert.assertEquals(value, sValue);
            });
            return null;
        });
        log.info("process [{}] simple string take time [{}s] ...", 30 * loop, simpleStrDuration);

        long simpleObjDuration = invoke(() -> {
            IntStream.range(0, 15 * loop).parallel().forEach(i -> {
                String uuid = TcIdWorker.upperCaseUuid();
                Mongo value = new Mongo(TcIdWorker.upperCaseUuid(),
                        Lists.newArrayList(new Mongo.Orange(TcIdWorker.upperCaseUuid())));
                tcCacheService.set(uuid, value, duration);
                Mongo sValue = tcCacheService.get(uuid, Mongo.class);
                Assert.assertEquals(value, sValue);
            });
            return null;
        });
        log.info("process [{}] simple object take time [{}s] ...", 15 * loop, simpleObjDuration);

        long objListDuration = invoke(() -> {
            IntStream.range(0, loop).parallel().forEach(i -> {
                String uuid = TcIdWorker.upperCaseUuid();
                ArrayList<Mongo> mongoList = Lists.newArrayList();
                for (int j = 0; j < 50; j++) {
                    Mongo value = new Mongo(TcIdWorker.upperCaseUuid(),
                            Lists.newArrayList(new Mongo.Orange(TcIdWorker.upperCaseUuid())));
                    mongoList.add(value);
                }
                tcCacheService.set(uuid, mongoList, duration);
                @SuppressWarnings("unchecked")
                List<Mongo> sValue = tcCacheService.get(uuid, List.class);
                Assert.assertEquals(mongoList, sValue);
            });
            return null;
        });
        log.info("process [{}] object list take time [{}s] ...", loop, objListDuration);
    }

    @Test
    public void get() throws Exception {
        set();
    }

    @Test
    public void exists() throws Exception {
        final int loop = 6;
        final long durationBase = 1000;
        final long durationOffset = 50;

        log.info("start ...");

        long simpleStrDuration = invoke(() -> {
            IntStream.range(0, 30 * loop).parallel().forEach(i -> {
                String uuid = TcIdWorker.upperCaseUuid();
                String value = TcIdWorker.upperCaseUuid();
                long duration = durationBase + RandomUtils.nextInt(1000);
                tcCacheService.set(uuid, value, duration);
                Assert.assertTrue(tcCacheService.exists(uuid));

                // consider network delay
                sleep(duration + durationOffset);

                Assert.assertTrue(!tcCacheService.exists(uuid));
            });
            return null;
        });
        log.info("process [{}] simple string take time [{}s] ...", 30 * loop, simpleStrDuration);
    }

    @Test
    public void expire() throws Exception {
        final int loop = 3;
        final long durationBase = 1000;
        final long durationOffset = 50;

        log.info("start ...");

        long simpleStrDuration = invoke(() -> {
            IntStream.range(0, 30 * loop).parallel().forEach(i -> {
                String uuid = TcIdWorker.upperCaseUuid();
                String value = TcIdWorker.upperCaseUuid();
                long duration = durationBase + RandomUtils.nextInt(1000);
                tcCacheService.set(uuid, value, duration);
                Assert.assertTrue(tcCacheService.exists(uuid));

                // consider network delay
                sleep(duration - durationOffset);

                Assert.assertTrue(tcCacheService.exists(uuid));

                tcCacheService.expire(uuid, duration);

                // consider network delay
                sleep(duration + durationOffset);

                Assert.assertTrue(!tcCacheService.exists(uuid));
            });
            return null;
        });
        log.info("process [{}] simple string take time [{}s] ...", 30 * loop, simpleStrDuration);

        tcCacheService.expire(TcIdWorker.upperCaseUuid(), 1000);
    }

    @Test
    public void delete() throws Exception {
        final int loop = 10;
        final long durationBase = 500;

        log.info("start ...");

        long simpleStrDuration = invoke(() -> {
            IntStream.range(0, 30 * loop).parallel().forEach(i -> {
                String uuid = TcIdWorker.upperCaseUuid();
                String value = TcIdWorker.upperCaseUuid();
                long duration = durationBase + RandomUtils.nextInt(500);
                tcCacheService.set(uuid, value, duration);
                Assert.assertTrue(tcCacheService.exists(uuid));

                sleep(RandomUtils.nextInt(1500));

                tcCacheService.delete(uuid);

                Assert.assertTrue(!tcCacheService.exists(uuid));
            });
            return null;
        });
        log.info("process [{}] simple string take time [{}s] ...", 30 * loop, simpleStrDuration);
    }

    @Test
    public void buildHashtagedKey() throws Exception {
        String hashtagedKey = tcCacheService.buildHashtag("key", "value");
        Assert.assertEquals("{key}value", hashtagedKey);
    }

    @Test
    public void getOpsForGroupedValue1() throws Exception {
        final int loop = 1000;
        final long durationBase = 5000;
        final String group1 = "group1";
        final String group2 = "group2";

        log.info("start ...");

        long simpleStrDuration = invoke(() -> {
            IntStream.range(0, 30 * loop).parallel().forEach(i -> {
                String uuid = TcIdWorker.upperCaseUuid();
                Mongo value = new Mongo(TcIdWorker.upperCaseUuid(),
                        Lists.newArrayList(new Mongo.Orange(TcIdWorker.upperCaseUuid())));

                tcCacheService.opsForGroupedValue().set(group1, uuid, value, durationBase);
                Mongo sValue1 = tcCacheService.opsForGroupedValue().get(group1, uuid, Mongo.class);
                Assert.assertEquals(value, sValue1);

                tcCacheService.opsForGroupedValue().set(group2, uuid, value, durationBase);
                Mongo sValue2 = tcCacheService.opsForGroupedValue().get(group2, uuid, Mongo.class);
                Assert.assertEquals(value, sValue2);
            });
            return null;
        });
        log.info("process [{}] simple string take time [{}s] ...", 30 * loop, simpleStrDuration);
    }

    @Test
    public void getOpsForGroupedValue2() throws Exception {
        final long durationBase = 2000;
        final String group1 = "group1";
        final String group2 = "group2";

        log.info("start ...");

        long simpleStrDuration = invoke(() -> {
            String uuid1 = TcIdWorker.upperCaseUuid();
            String uuid2 = TcIdWorker.upperCaseUuid();
            String uuid3 = TcIdWorker.upperCaseUuid();
            Mongo value1 = new Mongo(TcIdWorker.upperCaseUuid(),
                    Lists.newArrayList(new Mongo.Orange(TcIdWorker.upperCaseUuid())));
            Mongo value2 = new Mongo(TcIdWorker.upperCaseUuid(),
                    Lists.newArrayList(new Mongo.Orange(TcIdWorker.upperCaseUuid())));
            Mongo value3 = new Mongo(TcIdWorker.upperCaseUuid(),
                    Lists.newArrayList(new Mongo.Orange(TcIdWorker.upperCaseUuid())));

            tcCacheService.opsForGroupedValue().set(group1, uuid1, value1, 2 * durationBase);
            tcCacheService.opsForGroupedValue().set(group1, uuid2, value2, 3 * durationBase);
            tcCacheService.opsForGroupedValue().set(group1, uuid3, value3, 4 * durationBase);

            Assert.assertTrue(tcCacheService.opsForGroupedValue().exists(group1, uuid1));
            Assert.assertTrue(tcCacheService.opsForGroupedValue().exists(group1, uuid2));
            Assert.assertTrue(tcCacheService.opsForGroupedValue().exists(group1, uuid3));

            Mongo sValue1 = tcCacheService.opsForGroupedValue().get(group1, uuid1, Mongo.class);
            Assert.assertEquals(value1, sValue1);

            Mongo sValue2 = tcCacheService.opsForGroupedValue().get(group1, uuid2, Mongo.class);
            Assert.assertEquals(value2, sValue2);

            Mongo sValue3 = tcCacheService.opsForGroupedValue().get(group1, uuid3, Mongo.class);
            Assert.assertEquals(value3, sValue3);

            Set<String> keys1 = tcCacheService.opsForGroupedValue().keys(group1);
            Assert.assertEquals(3, keys1.size());
            Set<String> keys2 = tcCacheService.opsForGroupedValue().keys(group2);
            Assert.assertEquals(0, keys2.size());
            return null;
        });
        log.info("process [{}] simple string take time [{}s] ...", 1, simpleStrDuration);
    }

    @Test
    public void getOpsForGroupedValue3() throws Exception {
        final long durationBase = 2000;
        final String group1 = "group1";
        final String group2 = "group2";
        final long offset = 100;

        getOpsForGroupedValue2();

        Assert.assertEquals(3, tcCacheService.opsForGroupedValue().keys(group1).size());

        sleep(2 * durationBase - offset);
        Assert.assertEquals(3, tcCacheService.opsForGroupedValue().keys(group1).size());

        sleep(durationBase);
        Assert.assertEquals(2, tcCacheService.opsForGroupedValue().keys(group1).size());

        sleep(durationBase);
        Assert.assertEquals(1, tcCacheService.opsForGroupedValue().keys(group1).size());

        sleep(offset);
        Assert.assertEquals(0, tcCacheService.opsForGroupedValue().keys(group1).size());

        Assert.assertEquals(0, tcCacheService.opsForGroupedValue().keys(group2).size());
    }

    @Test
    public void getOpsForGroupedValue4() throws Exception {
        final long durationBase = 2000;
        final String group1 = "group1";

        getOpsForGroupedValue2();

        Assert.assertEquals(3, tcCacheService.opsForGroupedValue().keys(group1).size());

        String uuid1 = TcIdWorker.upperCaseUuid();
        Mongo value1 = new Mongo(TcIdWorker.upperCaseUuid(),
                Lists.newArrayList(new Mongo.Orange(TcIdWorker.upperCaseUuid())));

        tcCacheService.opsForGroupedValue().set(group1, uuid1, value1, 2 * durationBase);

        Assert.assertEquals(4, tcCacheService.opsForGroupedValue().keys(group1).size());

        tcCacheService.opsForGroupedValue().delete(group1, uuid1);

        Assert.assertEquals(3, tcCacheService.opsForGroupedValue().keys(group1).size());

        tcCacheService.opsForGroupedValue().delete(group1);

        Assert.assertEquals(0, tcCacheService.opsForGroupedValue().keys(group1).size());
    }

    @Test
    public void getOpsForGroupedValue5() throws Exception {
        final long durationBase = 2000;
        final String group1 = "group1";

        String uuid1 = TcIdWorker.upperCaseUuid();
        Mongo value1 = new Mongo(TcIdWorker.upperCaseUuid(),
                Lists.newArrayList(new Mongo.Orange(TcIdWorker.upperCaseUuid())));

        tcCacheService.opsForGroupedValue().set(group1, uuid1, value1, 2 * durationBase);

        Assert.assertEquals(4, tcCacheService.opsForGroupedValue().keys(group1).size());

        tcCacheService.opsForGroupedValue().delete(group1, uuid1);

        Assert.assertEquals(3, tcCacheService.opsForGroupedValue().keys(group1).size());

        tcCacheService.opsForGroupedValue().delete(group1);

        Assert.assertEquals(0, tcCacheService.opsForGroupedValue().keys(group1).size());
    }

    @Test
    public void getOpsForGroupedValue6() throws Exception {
        final long durationBase = 2000;
        final String group1 = "group1";

        String uuid1 = TcIdWorker.upperCaseUuid();
        Mongo value1 = new Mongo(TcIdWorker.upperCaseUuid(),
                Lists.newArrayList(new Mongo.Orange(TcIdWorker.upperCaseUuid())));

        tcCacheService.opsForGroupedValue().set(group1, uuid1, value1, durationBase);

        Assert.assertTrue(tcCacheService.opsForGroupedValue().exists(group1, uuid1));

        sleep(durationBase - 50);

        tcCacheService.opsForGroupedValue().expire(group1, uuid1, durationBase);

        sleep(durationBase - 50);

        Assert.assertTrue(tcCacheService.opsForGroupedValue().exists(group1, uuid1));
    }

    @Test
    public void getOpsForGroupedValue7() throws Exception {
        final int loop = 500;
        final long durationBase = 2000;
        final String group1 = "group1";

        IntStream.range(0, 30 * loop).parallel().forEach(i -> {
            String uuid = TcIdWorker.upperCaseUuid();
            Mongo value = new Mongo(TcIdWorker.upperCaseUuid(),
                    Lists.newArrayList(new Mongo.Orange(TcIdWorker.upperCaseUuid())));

            tcCacheService.opsForGroupedValue().set(group1, uuid, value, durationBase + RandomUtils.nextInt(2000));
        });

        Assert.assertTrue(tcCacheService.opsForGroupedValue().keys(group1).size() > 0);

        tcCacheService.opsForGroupedValue().delete(group1);
        Assert.assertEquals(0, tcCacheService.opsForGroupedValue().keys(group1).size());
    }

    @Test
    public void getOpsForGroupedValue8() throws Exception {
        final int loop = 500;
        final long durationBase = 2000;
        final String group1 = "group1";
        final String group2 = "group2";

        IntStream.range(0, 30 * loop).parallel().forEach(i -> {
            String uuid = TcIdWorker.upperCaseUuid();
            Mongo value = new Mongo(TcIdWorker.upperCaseUuid(),
                    Lists.newArrayList(new Mongo.Orange(TcIdWorker.upperCaseUuid())));

            tcCacheService.opsForGroupedValue().set(group1, uuid, value, durationBase + RandomUtils.nextInt(2000));
        });

        IntStream.range(0, 30 * loop).parallel().forEach(i -> {
            String uuid = TcIdWorker.upperCaseUuid();
            Mongo value = new Mongo(TcIdWorker.upperCaseUuid(),
                    Lists.newArrayList(new Mongo.Orange(TcIdWorker.upperCaseUuid())));

            tcCacheService.opsForGroupedValue().set(group2, uuid, value, durationBase + RandomUtils.nextInt(2000));
        });

        String uuid = TcIdWorker.upperCaseUuid();
        Mongo value = new Mongo(TcIdWorker.upperCaseUuid(),
                Lists.newArrayList(new Mongo.Orange(TcIdWorker.upperCaseUuid())));
        tcCacheService.opsForGroupedValue().set(group1, uuid, value, durationBase + RandomUtils.nextInt(2000));

        Assert.assertTrue(tcCacheService.opsForGroupedValue().exists(group1, uuid));

        Assert.assertTrue(tcCacheService.opsForGroupedValue().keys(group1).size() > 0);
        Assert.assertTrue(tcCacheService.opsForGroupedValue().keys(group2).size() > 0);

        tcCacheService.opsForGroupedValue().delete(group1);

        Assert.assertFalse(tcCacheService.opsForGroupedValue().exists(group1, uuid));
        Assert.assertEquals(0, tcCacheService.opsForGroupedValue().keys(group1).size());

        Assert.assertTrue(tcCacheService.opsForGroupedValue().keys(group2).size() > 0);
    }

    @Test
    public void testGuavaCache() {
    }

    @SneakyThrows
    private void sleep(long timeout) {
        TimeUnit.MILLISECONDS.sleep(timeout);
    }

}