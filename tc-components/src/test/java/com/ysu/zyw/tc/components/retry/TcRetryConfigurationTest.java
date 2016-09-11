package com.ysu.zyw.tc.components.retry;

import com.ysu.zyw.tc.sys.ex.TcException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "classpath*:config/tc-spring-common-retry.xml",
        "classpath*:config/tc-spring-import-retry.xml"
})
public class TcRetryConfigurationTest {

    @Resource
    private RetryTemplate retryTemplate;

    @Resource
    private RetryStub retryStub;

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test(expected = Exception.class)
    public void testRetryAnnotation2() {
        retryStub.sayHelo1();
    }

    @Test
    public void testRetryAnnotation4() {
        retryStub.sayHelo2();
    }

    @Test
    public void testRetryTemplate3() throws Exception {
        Integer r = retryTemplate.execute((RetryCallback<Integer, TcException>) context -> retryStub.sayHelo3(),
                context -> {
                    System.out.println("recover 3");
                    return null;
                });
        System.out.println(r);
    }

    @Test
    public void testRetryTemplate4() throws Exception {
        Integer r = retryTemplate.execute((RetryCallback<Integer, Exception>) context -> retryStub.sayHelo4(),
                context -> {
                    System.out.println("recover 4");
                    return null;
                });
        System.out.println(r);
    }

    @Test(expected = TcException.class)
    public void testRetryTemplate5() throws Exception {
        Integer r = retryTemplate.execute((RetryCallback<Integer, Exception>) context -> retryStub.sayHelo4(),
                context -> {
                    throw new TcException("tc");
                });
        System.out.println(r);
    }

}