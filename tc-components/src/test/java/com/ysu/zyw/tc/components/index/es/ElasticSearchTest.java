package com.ysu.zyw.tc.components.index.es;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.net.UnknownHostException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "classpath*:config/tc-spring-data-es.xml",
        "classpath*:config/tc-spring-import-es.xml"
})
public class ElasticSearchTest {

    @Resource
    private DataSource dataSource;

    @Resource
    private ElasticsearchTemplate elasticsearchTemplate;

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void test1() throws UnknownHostException {
    }

}
