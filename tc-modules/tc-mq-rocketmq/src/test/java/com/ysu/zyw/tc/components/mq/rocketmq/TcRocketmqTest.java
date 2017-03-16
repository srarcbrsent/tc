package com.ysu.zyw.tc.components.mq.rocketmq;

import lombok.extern.slf4j.Slf4j;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "classpath*:/config/tc-import-mq-rocketmq.xml",
        "classpath*:/config/tc-spring-mq-rocketmq.xml"
})
@Slf4j
public class TcRocketmqTest {
}
