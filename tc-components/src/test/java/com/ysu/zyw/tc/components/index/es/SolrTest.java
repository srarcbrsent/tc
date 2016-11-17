package com.ysu.zyw.tc.components.index.es;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import javax.sql.DataSource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "classpath*:config/tc-spring-data-solr.xml",
        "classpath*:config/tc-spring-import-solr.xml"
})
public class SolrTest {

    @Resource
    private DataSource dataSource;

}
