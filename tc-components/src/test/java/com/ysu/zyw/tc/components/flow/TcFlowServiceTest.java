package com.ysu.zyw.tc.components.flow;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "classpath*:config/tc-spring-flow.xml",
        "classpath*:config/tc-spring-data-mongodb.xml",
        "classpath*:config/tc-spring-import-data-mongodb.xml"
})
public class TcFlowServiceTest {

    @Resource
    private TcFlowService tcFlowService;

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void executeFlow() throws Exception {

    }

    @Test
    public void updateFlowData() throws Exception {

    }

    @Test
    public void findFlowWithFilter() throws Exception {

    }

}