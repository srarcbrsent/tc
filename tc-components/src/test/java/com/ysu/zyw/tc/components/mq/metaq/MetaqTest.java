package com.ysu.zyw.tc.components.mq.metaq;

import com.taobao.metamorphosis.client.extension.spring.MessageBuilder;
import com.taobao.metamorphosis.client.extension.spring.MetaqTemplate;
import com.ysu.zyw.tc.base.tools.IdWorker;
import com.ysu.zyw.tc.components.mq.metaq.model.TcApiCallMessage;
import com.ysu.zyw.tc.components.mq.metaq.model.TcLogMessage;
import com.ysu.zyw.tc.components.mq.metaq.subcribers.TcApiCallListener;
import com.ysu.zyw.tc.components.mq.metaq.subcribers.TcLogListener;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "classpath*:config/tc-spring-metaq.xml",
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
        final int loop = 3000;

        ExecutorService executorService = Executors.newFixedThreadPool(lo + lo);

        // publish 15 * 5000 log message
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

        // publish 15 * 5000 api call message
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

        executorService.shutdown();
        executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);

        while (!allMessageReceived(lo * loop, lo * loop)) {
            TimeUnit.SECONDS.sleep(1);
        }

        long l = Duration.between(localDateTime, LocalDateTime.now()).get(ChronoUnit.SECONDS);
        log.info("publish and receive [{}] take time [{}s]", 2 * lo * loop, l);
    }

    private boolean allMessageReceived(int logMsg, int apiCallMsg) {
        return TcLogListener.alreadyReceived >= logMsg && TcApiCallListener.alreadyReceived >= apiCallMsg;
    }

}
