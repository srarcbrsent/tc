package com.ysu.zyw.tc.components.aliyun.oss;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.core.io.FileSystemResource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

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
    public void test() throws IOException {
        Assert.assertTrue(tcAliyunOssService.exists(DEFAULT_BUCKET, DEMO_FOLDER));

        // upload
        String filename = UUID.randomUUID().toString().concat(".jpg");

        org.springframework.core.io.Resource rs =
                new FileSystemResource("/Users/zhangyaowu/Desktop/IMG_0154.JPG");
        tcAliyunOssService.set(
                DEFAULT_BUCKET,
                DEMO_FOLDER,
                filename,
                rs.getInputStream());

        Assert.assertTrue(tcAliyunOssService.exists(DEFAULT_BUCKET, DEMO_FOLDER, filename));

        try (InputStream inputStream = tcAliyunOssService.get(DEFAULT_BUCKET, DEMO_FOLDER, filename)) {
            FileOutputStream fos = new FileOutputStream("/Users/zhangyaowu/Desktop/1.JPG");
            IOUtils.copy(inputStream, fos);
        }

        tcAliyunOssService.del(DEFAULT_BUCKET, DEMO_FOLDER, filename);

        Assert.assertFalse(tcAliyunOssService.exists(DEFAULT_BUCKET, DEMO_FOLDER, filename));
    }

}