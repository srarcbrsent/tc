package com.ysu.zyw.tc.components.cache;

import com.ysu.zyw.tc.components.cache.codis.TcCodisCache;
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
public class TcCodisCacheTest {

    @Resource
    private TcCodisCache tcCodisCache;

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void getName() throws Exception {

    }

    @Test
    public void getNativeCache() throws Exception {

    }

    @Test
    public void get() throws Exception {

    }

    @Test
    public void get1() throws Exception {

    }

    @Test
    public void get2() throws Exception {

    }

    @Test
    public void put() throws Exception {

    }

    @Test
    public void putIfAbsent() throws Exception {

    }

    @Test
    public void evict() throws Exception {

    }

    @Test
    public void clear() throws Exception {

    }

}