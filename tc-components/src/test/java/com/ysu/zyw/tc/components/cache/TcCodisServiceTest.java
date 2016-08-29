package com.ysu.zyw.tc.components.cache;

import com.ysu.zyw.tc.components.cache.codis.TcCodisService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "classpath*:config/tc-spring-cache.xml",
        "classpath*:config/tc-spring-import-cache.xml"
})
public class TcCodisServiceTest {

    @Resource
    private TcCodisService tcCodisService;

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void set() throws Exception {

    }

    @Test
    public void get() throws Exception {

    }

    @Test
    public void exists() throws Exception {

    }

    @Test
    public void expire() throws Exception {

    }

    @Test
    public void delete() throws Exception {

    }

    @Test
    public void buildGroupedKey() throws Exception {

    }

    @Test
    public void buildHashtagedKey() throws Exception {

    }

    @Test
    public void getCodisTemplate() throws Exception {

    }

    @Test
    public void getOpsForGroupedValue() throws Exception {

    }

    @Test
    public void setCodisTemplate() throws Exception {

    }

}