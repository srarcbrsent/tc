package com.ysu.zyw.tc.components.sch.elasticjob.test;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.concurrent.TimeUnit;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "classpath*:/config/tc-import-sch-elasticjob.xml",
        "classpath*:/config/tc-spring-sch-elasticjob.xml"
})
@Slf4j
public class TcJobRunner {

    @Test
    public void test1() throws InterruptedException {
        TimeUnit.SECONDS.sleep(30);
    }

    @Test
    public void test2() throws InterruptedException {
        TimeUnit.SECONDS.sleep(30);
    }

}
