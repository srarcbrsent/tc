package com.ysu.zyw.tc.components.mq.amq;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import javax.jms.Destination;
import javax.jms.JMSException;
import java.io.Serializable;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.IntStream;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "classpath*:config/tc-spring-mq-amq.xml",
        "classpath*:config/tc-spring-import-amq.xml"
})
@Slf4j
public class AmqTest {

    @Resource
    private JmsTemplate jmsTemplate;

    @Resource(name = "queueDestination")
    private Destination queueDestination;

    @Resource(name = "topicDestination")
    private Destination topicDestination;

    @Resource
    private CachingConnectionFactory connectionFactory;

    private AtomicLong produced = new AtomicLong(0);

    @Test
    public void test01() throws JMSException {
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        IntStream.range(0, 5).forEach(i -> {
            IntStream.range(0, 100).forEach(j -> {
                jmsTemplate.convertAndSend(topicDestination,
                        new AmqMsg()
                                .setField1(i * 1000 + j)
                                .setField2(2)
                                .setField3("3")
                                .setField4(4.4)
                                .setField5(new Date())
                );
                produced.addAndGet(1);
            });
        });
        System.out.println(produced.get());
        sleep(11900);
    }

    private void sleep(long millis) {
        try {
            TimeUnit.MILLISECONDS.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Data
    @Accessors(chain = true)
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AmqMsg implements Serializable {

        private int field1;

        private long field2;

        private String field3;

        private double field4;

        private Date field5;

    }

}
