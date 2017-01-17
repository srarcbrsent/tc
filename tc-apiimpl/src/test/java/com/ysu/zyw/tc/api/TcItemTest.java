package com.ysu.zyw.tc.api;

import com.google.common.collect.Maps;
import com.ysu.zyw.tc.api.dao.mappers.TcItemMapper;
import com.ysu.zyw.tc.api.svc.items.TcItemService;
import com.ysu.zyw.tc.base.tools.TcIdGen;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.RandomUtils;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexResponse;
import org.elasticsearch.action.admin.indices.get.GetIndexResponse;
import org.elasticsearch.action.admin.indices.mapping.get.GetMappingsResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.QueryBuilders;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "classpath*:/config/tc-spring-deploy.xml",
        "classpath*:/config/tc-spring-cache-redis.xml",
        "classpath*:/config/tc-spring-se-elasticsearch.xml"
})
@Slf4j
public class TcItemTest {

    private static final String ES_INDEX = "demo_index";

    private static final String ES_TYPE = "demo_type";

    @Resource
    private TcItemService tcItemService;

    @Resource
    private TcItemMapper tcItemMapper;

    @Resource
    private TransportClient transportClient;

    @Before
    public void setUp() {
    }

    @Test
    public void testES() throws IOException {
        DeleteIndexResponse deleteIndexResponse = transportClient.admin()
                .indices()
                .prepareDelete(ES_INDEX)
                .get(TimeValue.timeValueMillis(3000));
        System.out.println(deleteIndexResponse.isAcknowledged());

        transportClient.listedNodes().forEach(System.out::println);

        System.out.println();

        GetIndexResponse getIndexResponse =
                transportClient.admin()
                        .indices()
                        .prepareGetIndex()
                        .get(TimeValue.timeValueMillis(1000));
        Arrays.stream(getIndexResponse.indices()).forEach(System.out::println);

        System.out.println();

        if (!ArrayUtils.contains(getIndexResponse.getIndices(), ES_INDEX)) {
            CreateIndexResponse createIndexResponse =
                    transportClient.admin()
                            .indices()
                            .prepareCreate(ES_INDEX)
                            .get(TimeValue.timeValueMillis(1000));
            System.out.println(createIndexResponse.isAcknowledged());
        }

        System.out.println();

        GetMappingsResponse getMappingsResponse =
                transportClient.admin()
                        .indices()
                        .prepareGetMappings(ES_INDEX)
                        .get(TimeValue.timeValueMillis(1000));

        if (!getMappingsResponse.getMappings().get(ES_INDEX).containsKey(ES_TYPE)) {
            transportClient.admin()
                    .indices()
                    .preparePutMapping(ES_INDEX)
                    .setType(ES_TYPE)
                    .setSource(
                            "{\n" +
                                    "        \"properties\": {\n" +
                                    "            \"level\": {\n" +
                                    "                \"type\": \"string\"\n" +
                                    "            },\n" +
                                    "            \"file\": {\n" +
                                    "                \"type\": \"string\"\n" +
                                    "            },\n" +
                                    "            \"line\": {\n" +
                                    "                \"type\": \"long\"\n" +
                                    "            },\n" +
                                    "            \"createdTimestamp\": {\n" +
                                    "                \"type\": \"date\"\n" +
                                    "            },\n" +
                                    "            \"msg\": {\n" +
                                    "                \"type\": \"text\"\n" +
                                    "            }\n" +
                                    "        }\n" +
                                    "    }"
                    )
                    .get(TimeValue.timeValueMillis(1000));
        }

        System.out.println(transportClient.admin()
                .indices()
                .prepareGetMappings(ES_INDEX)
                .get(TimeValue.timeValueMillis(1000)).getMappings().get(ES_INDEX).get(ES_TYPE).getSourceAsMap());

        System.out.println();
    }

    @Test
    public void testCreateDocument() {
        final String[] levels = new String[]{"ERROR", "WARN", "INFO", "DEBUG"};

        for (int i = 0; i < 500; i++) {
            String id = TcIdGen.upperCaseUuid();
            Map<String, Object> source = Maps.newHashMap();
            source.put("level", levels[RandomUtils.nextInt(0, levels.length - 1)]);
            source.put("file", this.getClass().getName());
            source.put("line", RandomUtils.nextLong(1, 200));
            source.put("createdTimestamp",
                    new Date(new Date().getTime() + RandomUtils.nextLong(0, 250000) - 50000));
            source.put("msg", "简直无情" + i);
            IndexResponse indexResponse = transportClient.prepareIndex(ES_INDEX, ES_TYPE, id)
                    .setSource(source)
                    .get(TimeValue.timeValueMillis(1000));
            System.out.println(indexResponse.getResult());
        }
    }

    @Test
    public void testSearchDocument() {
        SearchResponse searchResponse = transportClient.prepareSearch(ES_INDEX)
                .setTypes(ES_TYPE)
                .setQuery(QueryBuilders.matchAllQuery())
                .setFrom(0)
                .setSize(10000)
                .get(TimeValue.timeValueMillis(3000));
        Arrays.stream(searchResponse.getHits().getHits()).forEach(searchHitFields -> {
            System.out.println(searchHitFields.getSourceAsString());
        });
    }

}
