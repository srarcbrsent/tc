package com.ysu.zyw.tc.components.cache;

import com.google.common.base.Throwables;
import com.google.common.collect.Lists;
import com.ysu.zyw.tc.base.tools.IdWorker;
import com.ysu.zyw.tc.components.cache.codis.TcCodisService;
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
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;
import java.util.stream.IntStream;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "classpath*:config/tc-spring-cache.xml",
        "classpath*:config/tc-spring-import-cache.xml"
})
@Slf4j
public class TcCodisServiceTest {

    @Resource
    private TcCodisService tcCodisService;

    private long invoke(Supplier<?> supplier) {
        LocalDateTime localDateTime = LocalDateTime.now();
        supplier.get();
        return Duration.between(localDateTime, LocalDateTime.now()).get(ChronoUnit.SECONDS);
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
                String uuid = IdWorker.upperCaseUuid();
                String value = IdWorker.upperCaseUuid();
                tcCodisService.set(uuid, value, duration);
                String sValue = tcCodisService.get(uuid, String.class);
                Assert.assertEquals(value, sValue);
            });
            return null;
        });
        log.info("process [{}] simple string take time [{}] ...", 30 * loop, simpleStrDuration);

        long simpleObjDuration = invoke(() -> {
            IntStream.range(0, 15 * loop).parallel().forEach(i -> {
                String uuid = IdWorker.upperCaseUuid();
                Mongo value = new Mongo(IdWorker.upperCaseUuid(),
                        Lists.newArrayList(new Mongo.Orange(IdWorker.upperCaseUuid())));
                tcCodisService.set(uuid, value, duration);
                Mongo sValue = tcCodisService.get(uuid, Mongo.class);
                Assert.assertEquals(value, sValue);
            });
            return null;
        });
        log.info("process [{}] simple object take time [{}] ...", 15 * loop, simpleObjDuration);

        long objListDuration = invoke(() -> {
            IntStream.range(0, loop).parallel().forEach(i -> {
                String uuid = IdWorker.upperCaseUuid();
                ArrayList<Mongo> mongoList = Lists.newArrayList();
                for (int j = 0; j < 50; j++) {
                    Mongo value = new Mongo(IdWorker.upperCaseUuid(),
                            Lists.newArrayList(new Mongo.Orange(IdWorker.upperCaseUuid())));
                    mongoList.add(value);
                }
                tcCodisService.set(uuid, mongoList, duration);
                @SuppressWarnings("unchecked")
                List<Mongo> sValue = tcCodisService.get(uuid, List.class);
                Assert.assertEquals(mongoList, sValue);
            });
            return null;
        });
        log.info("process [{}] object list take time [{}] ...", loop, objListDuration);
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
                String uuid = IdWorker.upperCaseUuid();
                String value = IdWorker.upperCaseUuid();
                long duration = durationBase + RandomUtils.nextInt(1000);
                tcCodisService.set(uuid, value, duration);
                Assert.assertTrue(tcCodisService.exists(uuid));

                // consider network delay
                sleep(duration + durationOffset);

                Assert.assertTrue(!tcCodisService.exists(uuid));
            });
            return null;
        });
        log.info("process [{}] simple string take time [{}] ...", 30 * loop, simpleStrDuration);
    }

    @Test
    public void expire() throws Exception {
        final int loop = 3;
        final long durationBase = 1000;
        final long durationOffset = 50;

        log.info("start ...");

        long simpleStrDuration = invoke(() -> {
            IntStream.range(0, 30 * loop).parallel().forEach(i -> {
                String uuid = IdWorker.upperCaseUuid();
                String value = IdWorker.upperCaseUuid();
                long duration = durationBase + RandomUtils.nextInt(1000);
                tcCodisService.set(uuid, value, duration);
                Assert.assertTrue(tcCodisService.exists(uuid));

                // consider network delay
                sleep(duration - durationOffset);

                Assert.assertTrue(tcCodisService.exists(uuid));

                tcCodisService.expire(uuid, duration);

                // consider network delay
                sleep(duration + durationOffset);

                Assert.assertTrue(!tcCodisService.exists(uuid));
            });
            return null;
        });
        log.info("process [{}] simple string take time [{}] ...", 30 * loop, simpleStrDuration);

        tcCodisService.expire(IdWorker.upperCaseUuid(), 1000);
    }

    @Test
    public void delete() throws Exception {
        final int loop = 10;
        final long durationBase = 500;

        log.info("start ...");

        long simpleStrDuration = invoke(() -> {
            IntStream.range(0, 30 * loop).parallel().forEach(i -> {
                String uuid = IdWorker.upperCaseUuid();
                String value = IdWorker.upperCaseUuid();
                long duration = durationBase + RandomUtils.nextInt(500);
                tcCodisService.set(uuid, value, duration);
                Assert.assertTrue(tcCodisService.exists(uuid));

                sleep(RandomUtils.nextInt(1500));

                tcCodisService.delete(uuid);

                Assert.assertTrue(!tcCodisService.exists(uuid));
            });
            return null;
        });
        log.info("process [{}] simple string take time [{}] ...", 30 * loop, simpleStrDuration);
    }

    @Test
    public void buildHashtagedKey() throws Exception {
        String hashtagedKey = tcCodisService.buildHashtagedKey("key", "value");
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
                String uuid = IdWorker.upperCaseUuid();
                Mongo value = new Mongo(IdWorker.upperCaseUuid(),
                        Lists.newArrayList(new Mongo.Orange(IdWorker.upperCaseUuid())));

                tcCodisService.opsForGroupedValue().set(group1, uuid, value, durationBase);
                Mongo sValue1 = tcCodisService.opsForGroupedValue().get(group1, uuid, Mongo.class);
                Assert.assertEquals(value, sValue1);

                tcCodisService.opsForGroupedValue().set(group2, uuid, value, durationBase);
                Mongo sValue2 = tcCodisService.opsForGroupedValue().get(group2, uuid, Mongo.class);
                Assert.assertEquals(value, sValue2);
            });
            return null;
        });
        log.info("process [{}] simple string take time [{}] ...", 30 * loop, simpleStrDuration);
    }

    @Test
    public void getOpsForGroupedValue2() throws Exception {
        final long durationBase = 2000;
        final String group1 = "group1";
        final String group2 = "group2";

        log.info("start ...");

        long simpleStrDuration = invoke(() -> {
            String uuid1 = IdWorker.upperCaseUuid();
            String uuid2 = IdWorker.upperCaseUuid();
            String uuid3 = IdWorker.upperCaseUuid();
            Mongo value1 = new Mongo(IdWorker.upperCaseUuid(),
                    Lists.newArrayList(new Mongo.Orange(IdWorker.upperCaseUuid())));
            Mongo value2 = new Mongo(IdWorker.upperCaseUuid(),
                    Lists.newArrayList(new Mongo.Orange(IdWorker.upperCaseUuid())));
            Mongo value3 = new Mongo(IdWorker.upperCaseUuid(),
                    Lists.newArrayList(new Mongo.Orange(IdWorker.upperCaseUuid())));

            tcCodisService.opsForGroupedValue().set(group1, uuid1, value1, 2 * durationBase);
            tcCodisService.opsForGroupedValue().set(group1, uuid2, value2, 3 * durationBase);
            tcCodisService.opsForGroupedValue().set(group1, uuid3, value3, 4 * durationBase);

            Assert.assertTrue(tcCodisService.opsForGroupedValue().exists(group1, uuid1));
            Assert.assertTrue(tcCodisService.opsForGroupedValue().exists(group1, uuid2));
            Assert.assertTrue(tcCodisService.opsForGroupedValue().exists(group1, uuid3));

            Mongo sValue1 = tcCodisService.opsForGroupedValue().get(group1, uuid1, Mongo.class);
            Assert.assertEquals(value1, sValue1);

            Mongo sValue2 = tcCodisService.opsForGroupedValue().get(group1, uuid2, Mongo.class);
            Assert.assertEquals(value2, sValue2);

            Mongo sValue3 = tcCodisService.opsForGroupedValue().get(group1, uuid3, Mongo.class);
            Assert.assertEquals(value3, sValue3);

            Set<String> keys1 = tcCodisService.opsForGroupedValue().keys(group1);
            Assert.assertEquals(3, keys1.size());
            Set<String> keys2 = tcCodisService.opsForGroupedValue().keys(group2);
            Assert.assertEquals(0, keys2.size());
            return null;
        });
        log.info("process [{}] simple string take time [{}] ...", 1, simpleStrDuration);
    }

    @Test
    public void getOpsForGroupedValue3() throws Exception {
        final long durationBase = 2000;
        final String group1 = "group1";
        final String group2 = "group2";
        final long offset = 100;

        getOpsForGroupedValue2();

        Assert.assertEquals(3, tcCodisService.opsForGroupedValue().keys(group1).size());

        sleep(2 * durationBase - offset);
        Assert.assertEquals(3, tcCodisService.opsForGroupedValue().keys(group1).size());

        sleep(durationBase);
        Assert.assertEquals(2, tcCodisService.opsForGroupedValue().keys(group1).size());

        sleep(durationBase);
        Assert.assertEquals(1, tcCodisService.opsForGroupedValue().keys(group1).size());

        sleep(offset);
        Assert.assertEquals(0, tcCodisService.opsForGroupedValue().keys(group1).size());

        Assert.assertEquals(0, tcCodisService.opsForGroupedValue().keys(group2).size());
    }

    @Test
    public void getOpsForGroupedValue4() throws Exception {
        final long durationBase = 2000;
        final String group1 = "group1";

        getOpsForGroupedValue2();

        Assert.assertEquals(3, tcCodisService.opsForGroupedValue().keys(group1).size());

        String uuid1 = IdWorker.upperCaseUuid();
        Mongo value1 = new Mongo(IdWorker.upperCaseUuid(),
                Lists.newArrayList(new Mongo.Orange(IdWorker.upperCaseUuid())));

        tcCodisService.opsForGroupedValue().set(group1, uuid1, value1, 2 * durationBase);

        Assert.assertEquals(4, tcCodisService.opsForGroupedValue().keys(group1).size());

        tcCodisService.opsForGroupedValue().delete(group1, uuid1);

        Assert.assertEquals(3, tcCodisService.opsForGroupedValue().keys(group1).size());

        tcCodisService.opsForGroupedValue().delete(group1);

        Assert.assertEquals(0, tcCodisService.opsForGroupedValue().keys(group1).size());
    }

    @Test
    public void getOpsForGroupedValue5() throws Exception {
        final long durationBase = 2000;
        final String group1 = "group1";

        String uuid1 = IdWorker.upperCaseUuid();
        Mongo value1 = new Mongo(IdWorker.upperCaseUuid(),
                Lists.newArrayList(new Mongo.Orange(IdWorker.upperCaseUuid())));

        tcCodisService.opsForGroupedValue().set(group1, uuid1, value1, 2 * durationBase);

        Assert.assertEquals(4, tcCodisService.opsForGroupedValue().keys(group1).size());

        tcCodisService.opsForGroupedValue().delete(group1, uuid1);

        Assert.assertEquals(3, tcCodisService.opsForGroupedValue().keys(group1).size());

        tcCodisService.opsForGroupedValue().delete(group1);

        Assert.assertEquals(0, tcCodisService.opsForGroupedValue().keys(group1).size());
    }

    @Test
    public void getOpsForGroupedValue6() throws Exception {
        final long durationBase = 2000;
        final String group1 = "group1";

        String uuid1 = IdWorker.upperCaseUuid();
        Mongo value1 = new Mongo(IdWorker.upperCaseUuid(),
                Lists.newArrayList(new Mongo.Orange(IdWorker.upperCaseUuid())));

        tcCodisService.opsForGroupedValue().set(group1, uuid1, value1, durationBase);

        Assert.assertTrue(tcCodisService.opsForGroupedValue().exists(group1, uuid1));

        sleep(durationBase - 50);

        tcCodisService.opsForGroupedValue().expire(group1, uuid1, durationBase);

        sleep(durationBase - 50);

        Assert.assertTrue(tcCodisService.opsForGroupedValue().exists(group1, uuid1));
    }

    @Test
    public void getOpsForGroupedValue7() throws Exception {
        final int loop = 500;
        final long durationBase = 2000;
        final String group1 = "group1";

        IntStream.range(0, 30 * loop).parallel().forEach(i -> {
            String uuid = IdWorker.upperCaseUuid();
            Mongo value = new Mongo(IdWorker.upperCaseUuid(),
                    Lists.newArrayList(new Mongo.Orange(IdWorker.upperCaseUuid())));

            tcCodisService.opsForGroupedValue().set(group1, uuid, value, durationBase + RandomUtils.nextInt(2000));
        });

        Assert.assertTrue(tcCodisService.opsForGroupedValue().keys(group1).size() > 0);

        tcCodisService.opsForGroupedValue().delete(group1);
        Assert.assertEquals(0, tcCodisService.opsForGroupedValue().keys(group1).size());
    }

    @Test
    public void getOpsForGroupedValue8() throws Exception {
        final int loop = 500;
        final long durationBase = 2000;
        final String group1 = "group1";
        final String group2 = "group2";

        IntStream.range(0, 30 * loop).parallel().forEach(i -> {
            String uuid = IdWorker.upperCaseUuid();
            Mongo value = new Mongo(IdWorker.upperCaseUuid(),
                    Lists.newArrayList(new Mongo.Orange(IdWorker.upperCaseUuid())));

            tcCodisService.opsForGroupedValue().set(group1, uuid, value, durationBase + RandomUtils.nextInt(2000));
        });

        IntStream.range(0, 30 * loop).parallel().forEach(i -> {
            String uuid = IdWorker.upperCaseUuid();
            Mongo value = new Mongo(IdWorker.upperCaseUuid(),
                    Lists.newArrayList(new Mongo.Orange(IdWorker.upperCaseUuid())));

            tcCodisService.opsForGroupedValue().set(group2, uuid, value, durationBase + RandomUtils.nextInt(2000));
        });

        String uuid = IdWorker.upperCaseUuid();
        Mongo value = new Mongo(IdWorker.upperCaseUuid(),
                Lists.newArrayList(new Mongo.Orange(IdWorker.upperCaseUuid())));
        tcCodisService.opsForGroupedValue().set(group1, uuid, value, durationBase + RandomUtils.nextInt(2000));

        Assert.assertTrue(tcCodisService.opsForGroupedValue().exists(group1, uuid));

        Assert.assertTrue(tcCodisService.opsForGroupedValue().keys(group1).size() > 0);
        Assert.assertTrue(tcCodisService.opsForGroupedValue().keys(group2).size() > 0);

        tcCodisService.opsForGroupedValue().delete(group1);

        Assert.assertFalse(tcCodisService.opsForGroupedValue().exists(group1, uuid));
        Assert.assertEquals(0, tcCodisService.opsForGroupedValue().keys(group1).size());

        Assert.assertTrue(tcCodisService.opsForGroupedValue().keys(group2).size() > 0);
    }

    private void sleep(long timeout) {
        try {
            TimeUnit.MILLISECONDS.sleep(timeout);
        } catch (InterruptedException e) {
            Throwables.propagate(e);
        }
    }

}