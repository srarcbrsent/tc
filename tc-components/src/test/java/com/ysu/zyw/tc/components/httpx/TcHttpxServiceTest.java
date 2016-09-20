package com.ysu.zyw.tc.components.httpx;

import lombok.Getter;
import lombok.Setter;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.beans.IntrospectionException;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "classpath*:config/tc-spring-rpc-httpx.xml",
        "classpath*:config/tc-spring-import-httpx.xml"
})
public class TcHttpxServiceTest {

    @Getter
    @Setter
    @Resource
    private TcHttpxService tcHttpxService;

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void getText4Entity() throws Exception {

    }

    @Test
    public void postText4Entity() throws Exception {

    }

    @Test
    public void postJson4Entity() throws Exception {

    }

    @Test
    public void testUriExpand() throws IntrospectionException {
    }

}