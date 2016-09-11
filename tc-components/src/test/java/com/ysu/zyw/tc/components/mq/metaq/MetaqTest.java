package com.ysu.zyw.tc.components.mq.metaq;

import com.taobao.metamorphosis.client.extension.spring.JavaSerializationMessageBodyConverter;
import com.taobao.metamorphosis.client.extension.spring.MessageBuilder;
import com.taobao.metamorphosis.client.extension.spring.MetaqTemplate;
import com.taobao.metamorphosis.exception.MetaClientException;
import com.ysu.zyw.tc.base.tools.IdWorker;
import com.ysu.zyw.tc.components.mq.metaq.model.TcApiCallMessage;
import com.ysu.zyw.tc.components.mq.metaq.model.TcLogMessage;
import com.ysu.zyw.tc.components.mq.metaq.subcribers.TcApiCallListener;
import com.ysu.zyw.tc.components.mq.metaq.subcribers.TcLogDataStorageListener;
import com.ysu.zyw.tc.components.mq.metaq.subcribers.TcLogRealTimeComputationListener;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "classpath*:config/tc-spring-mq-metaq.xml",
        "classpath*:config/tc-spring-import-metaq.xml"
})
@Slf4j
public class MetaqTest {

    public static final String TOPIC_LOG = "topic-log";

    public static final String TOPIC_API_CALL = "topic-api-call";

    public static final String GROUP_REAL_TIME_COMPUTATION = "group-real-time-computation";

    public static final String GROUP_DATA_STORAGE = "group-data-storage";

    @Resource
    private MetaqTemplate metaqTemplate;

    @Test(timeout = 60000L)
    public void test1() throws InterruptedException {
        LocalDateTime localDateTime = LocalDateTime.now();

        final int lo = 15;
        final int loop = 2000;

        ExecutorService executorService = Executors.newFixedThreadPool(lo + lo);

        // publish log message
        IntStream.range(0, lo).forEach(i -> {
            executorService.execute(() -> {
                IntStream.range(0, loop).forEach(j -> {
                    try {
                        metaqTemplate.send(MessageBuilder
                                .withTopic(TOPIC_LOG)
                                .withAttribute(IdWorker.upperCaseUuid())
                                .withBody(new TcLogMessage("log")));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });
            });
        });

        // publish api call message
        IntStream.range(0, lo).forEach(i -> {
            executorService.execute(() -> {
                IntStream.range(0, loop).forEach(j -> {
                    try {
                        metaqTemplate.send(MessageBuilder
                                .withTopic(TOPIC_API_CALL)
                                .withAttribute(IdWorker.upperCaseUuid())
                                .withBody(new TcApiCallMessage("api")));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });
            });
        });

        // double producer, three consumer.
        executorService.shutdown();
        executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);

        while (!allMessageReceived(lo * loop, lo * loop, lo * loop)) {
            TimeUnit.SECONDS.sleep(1);
        }

        long l = Duration.between(localDateTime, LocalDateTime.now()).get(ChronoUnit.SECONDS);
        log.info("publish and receive [{}] take time [{}s]", 3 * lo * loop, l);
    }

    @Test
    public void test2() throws InterruptedException, MetaClientException {
        JavaSerializationMessageBodyConverter javaSerializationMessageBodyConverter =
                new JavaSerializationMessageBodyConverter();
        byte[] bytes = javaSerializationMessageBodyConverter.toByteArray(
                new MetaBody(1, 2L, IdWorker.upperCaseUuid(), 4.5, new Date())
        );
        //noinspection unused
        MetaBody metaBody = (MetaBody) javaSerializationMessageBodyConverter.fromByteArray(bytes);
        Assert.assertTrue(bytes.length + 20 < 400);
    }

    @Test
    public void test3() throws MetaClientException {
        TcHessianSerializationMessageBodyConverter<Object> tcJsonSerializationMessageBodyConverter =
                new TcHessianSerializationMessageBodyConverter<>();
        byte[] bytes = tcJsonSerializationMessageBodyConverter.toByteArray(
                new MetaBody(1, 2L, IdWorker.upperCaseUuid(), 4.5, new Date()));
        MetaBody metaBody = (MetaBody) tcJsonSerializationMessageBodyConverter.fromByteArray(bytes);
        System.out.println(metaBody);
    }

    @Test
    public void test4() throws MetaClientException {
        MetaBody metaBody = new MetaBody(1, 2L, IdWorker.upperCaseUuid(), 4.5, new Date());
        JavaSerializationMessageBodyConverter javaSerializationMessageBodyConverter =
                new JavaSerializationMessageBodyConverter();
        TcHessianSerializationMessageBodyConverter<Object> tcHessianSerializationMessageBodyConverter =
                new TcHessianSerializationMessageBodyConverter<>();

        final int loop = 5;

        LocalDateTime now1 = LocalDateTime.now();
        for (int i = 0; i < loop; i++) {
            byte[] bytes = javaSerializationMessageBodyConverter.toByteArray(metaBody);
            System.out.println(bytes.length);
        }
        long javaSerializationTakeNanos = Duration.between(now1, LocalDateTime.now()).get(ChronoUnit.NANOS);

        LocalDateTime now2 = LocalDateTime.now();
        for (int i = 0; i < loop; i++) {
            byte[] bytes = tcHessianSerializationMessageBodyConverter.toByteArray(metaBody);
            System.out.println(bytes.length);
        }
        long hessian2SerializationTakeNanos = Duration.between(now2, LocalDateTime.now()).get(ChronoUnit.NANOS);

        log.info("java [{}ns], hessian [{}ns]", javaSerializationTakeNanos, hessian2SerializationTakeNanos);
    }

    private boolean allMessageReceived(int logRealTimeComputationMsg, int logDataStorageMsg, int apiCallMsg) {
        log.info("current consumed message [{}] [{}] [{}]",
                TcLogRealTimeComputationListener.alreadyReceived,
                TcLogDataStorageListener.alreadyReceived,
                TcApiCallListener.alreadyReceived);
        return TcLogRealTimeComputationListener.alreadyReceived.get() >= logRealTimeComputationMsg
                && TcLogDataStorageListener.alreadyReceived.get() >= logDataStorageMsg
                && TcApiCallListener.alreadyReceived.get() >= apiCallMsg;
    }

    @Data
    @Accessors(chain = true)
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MetaBody implements Serializable {

        private int field1;

        private long field2;

        private String field3;

        private double field4;

        private Date field5;

    }

}
