package com.ysu.zyw.tc.api.test;

import com.ysu.zyw.tc.api.dao.mappers.TcItemMapper;
import com.ysu.zyw.tc.api.svc.items.TcItemService;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.Arrays;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "classpath*:/config/tc-spring-deploy.xml",
        "classpath*:/config/tc-spring-cache-redis.xml",
        "classpath*:/config/tc-spring-se-elasticsearch.xml"
})
@Slf4j
public class TcItemTest {

    private static final String ES_INDEX = "demo_index";

    private static final String ES_TYPE = "demo_doc";

    @Resource
    private TcItemService tcItemService;

    @Resource
    private TcItemMapper tcItemMapper;

    @Resource
    private TransportClient transportClient;

    @Test
    public void testES() {
//        TcItem tcItem = tcItemMapper.selectByPrimaryKey("00002D472C7B48D896A48B5F5FE0A522");
//        IndexResponse indexResponse = transportClient.prepareIndex(ES_INDEX, ES_DOCUMENT, "00002D472C7B48D896A48B5F5FE0A522")
//                .setSource(TcSerializationUtils.writeJson(tcItem))
//                .get();
//        System.out.println(indexResponse.getId());
//        System.out.println(indexResponse.getIndex());
//        System.out.println(indexResponse.getType());
//        System.out.println(indexResponse.getVersion());
//        System.out.println(indexResponse.getResult());
        SearchResponse searchResponse = transportClient.prepareSearch(ES_INDEX)
                .setTypes(ES_TYPE)
                .setQuery(QueryBuilders.matchQuery("shopId", "A1861BD5CCE44B399E891DAAD99BAE27"))
                .get();
        System.out.println(searchResponse.getHits().getHits().length);
        Arrays.stream(searchResponse.getHits().getHits())
                .forEach(searchHitFields -> System.out.println(searchHitFields.getId()));
    }

}
