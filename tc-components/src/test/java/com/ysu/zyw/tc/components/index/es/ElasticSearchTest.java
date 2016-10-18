package com.ysu.zyw.tc.components.index.es;

import org.apache.commons.lang.math.RandomUtils;
import org.elasticsearch.index.query.MatchAllQueryBuilder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

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
//        if (elasticsearchTemplate.indexExists(Tank.class)) {
//            elasticsearchTemplate.deleteIndex(Tank.class);
//        }
//        elasticsearchTemplate.createIndex(Tank.class);
//        elasticsearchTemplate.putMapping(Tank.class);
    }

    @Test
    public void test1() {
        NativeSearchQuery nativeSearchQuery =
                new NativeSearchQueryBuilder()
                        .withQuery(new MatchAllQueryBuilder())
                        .withPageable(new PageRequest(0, 1000))
                        .build();
        Page<Tank> tanks = elasticsearchTemplate.queryForPage(nativeSearchQuery, Tank.class);
        tanks.forEach(System.out::println);
    }

    @Test
    public void test2() throws InterruptedException {
        for (int i = 0; i < 600; i++) {
            elasticsearchTemplate.index(new IndexQueryBuilder().withObject(
                    new Tank(UUID.randomUUID().toString(), RandomUtils.nextLong(), new Date())
            ).build());
            TimeUnit.MILLISECONDS.sleep(20);
        }
    }

}
