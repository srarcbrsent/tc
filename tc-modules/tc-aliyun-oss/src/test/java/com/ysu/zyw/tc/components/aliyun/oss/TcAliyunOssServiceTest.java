package com.ysu.zyw.tc.components.aliyun.oss;

import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "classpath*:/config/tc-import-aliyun-oss.xml",
        "classpath*:/config/tc-spring-aliyun-oss.xml"
})
@Slf4j
public class TcAliyunOssServiceTest {

    private static final String DEFAULT_BUCKET = "tc-resources";

    private static final String DEMO_FOLDER = "/helloworld";

    @Resource
    private TcAliyunOssService tcAliyunOssService;

    @Before
    public void setUp() throws Exception {
        tcAliyunOssService.set(DEFAULT_BUCKET, DEMO_FOLDER);
    }

    @After
    public void tearDown() throws Exception {
        tcAliyunOssService.del(DEFAULT_BUCKET, DEMO_FOLDER);
    }

    @Test
    public void test() {
        Assert.assertTrue(tcAliyunOssService.exists(DEFAULT_BUCKET, DEMO_FOLDER));
    }

}