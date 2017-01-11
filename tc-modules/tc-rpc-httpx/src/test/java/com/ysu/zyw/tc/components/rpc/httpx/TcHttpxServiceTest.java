package com.ysu.zyw.tc.components.rpc.httpx;

import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "classpath*:/config/tc-import-rpc-httpx.xml",
        "classpath*:/config/tc-spring-rpc-httpx.xml"
})
@Slf4j
public class TcHttpxServiceTest {

    @Resource
    private TcHttpxService tcHttpxService;

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

}