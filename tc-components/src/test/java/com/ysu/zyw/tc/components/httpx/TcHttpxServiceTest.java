package com.ysu.zyw.tc.components.httpx;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.ysu.zyw.tc.base.tools.TcIdWorker;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang.math.RandomUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.MultiValueMap;

import javax.annotation.Resource;
import java.beans.IntrospectionException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "classpath*:config/tc-spring-rpc-httpx.xml",
        "classpath*:config/tc-spring-import-httpx.xml"
})
public class TcHttpxServiceTest {

    @Getter
    @Setter
    @Resource
    private TcHttpxService tcHttpxService;

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void getText4Entity() throws Exception {

    }

    @Test
    public void postText4Entity() throws Exception {

    }

    @Test
    public void postJson4Entity() throws Exception {

    }

    @Test
    public void testUriExpand() throws IntrospectionException {
    }

    @Test
    public void testWrapObj2MultiValueMap() {
        MultiValueMap<String, String> multiValueMap1 = tcHttpxService.copy2MultiValueMap(null);
        System.out.println(multiValueMap1);
        Assert.assertTrue(multiValueMap1.size() == 0);

        HashMap<String, Object> map1 = Maps.newHashMap();
        map1.put("key1", "value1");
        map1.put("key2", 2);
        MultiValueMap<String, String> multiValueMap2 = tcHttpxService.copy2MultiValueMap(map1);
        System.out.println(multiValueMap2);
        Assert.assertTrue(multiValueMap2.size() == 2);

        HashMap<String, Object> map2 = Maps.newHashMap();
        map2.put("key31", "value2");
        map2.put("key32", 2);
        map1.put("key3", map2);
        MultiValueMap<String, String> multiValueMap3 = tcHttpxService.copy2MultiValueMap(map1);
        System.out.println(multiValueMap3);
        Assert.assertTrue(multiValueMap3.size() == 4);

        List<Object> list1 = Lists.newArrayList("1", 2);
        map1.put("key4", list1);
        MultiValueMap<String, String> multiValueMap4 = tcHttpxService.copy2MultiValueMap(map1);
        System.out.println(multiValueMap4);
        Assert.assertTrue(multiValueMap4.size() == 6);

        List<Map<String, Object>> list2 = Lists.newArrayList();
        list2.add(map2);
        map1.put("key5", list2);
        MultiValueMap<String, String> multiValueMap5 = tcHttpxService.copy2MultiValueMap(map1);
        System.out.println(multiValueMap5);
        Assert.assertTrue(multiValueMap5.size() == 8);

        // build apple
        Map<String, Apple.Aple> apleMap = Maps.newHashMap();
        apleMap.put("helo1", new Apple.Aple(RandomUtils.nextInt(50), true, true));
        apleMap.put("helo2", new Apple.Aple(RandomUtils.nextInt(50), true, true));
        apleMap.put("helo3", new Apple.Aple(RandomUtils.nextInt(50), true, true));
        @SuppressWarnings(value = "unchecked")
        Apple apple = new Apple(RandomUtils.nextInt(500),
                TcIdWorker.upperCaseUuid(),
                new Apple.Aple(RandomUtils.nextInt(), false, true),
                (List) Lists.newArrayList("1", "2"),
                (List) Lists.newArrayList(
                        new Apple.Aple(RandomUtils.nextInt(500), false, true)
                ),
                apleMap
        );
        MultiValueMap<String, String> multiValueMap6 = tcHttpxService.copy2MultiValueMap(apple);
        System.out.println(multiValueMap6);

        // build orange
        HashMap<String, Orange.Tank.Chair> chairMap = Maps.newHashMap();
        chairMap.put(TcIdWorker.upperCaseUuid(), new Orange.Tank.Chair(new boolean[]{true}));
        chairMap.put(TcIdWorker.upperCaseUuid(), new Orange.Tank.Chair(new boolean[]{true, false}));
        Orange orange = new Orange(
                TcIdWorker.upperCaseUuid(),
                Lists.newArrayList(
                        new Orange.Tank(RandomUtils.nextLong(), chairMap),
                        new Orange.Tank(RandomUtils.nextLong(), chairMap)
                )
        );
        MultiValueMap<String, String> multiValueMap7 = tcHttpxService.copy2MultiValueMap(orange);
        System.out.println(multiValueMap7);
    }

    /**
     * liyunfei_zdd1 acd2031e987e44b09b4f8a033d943c81
     */
    @Test
    public void testPushCoupon() {
        for (int i = 0; i < 120; i++) {
            pushCoupon();
        }
    }

    private void pushCoupon() {
        final String PROMOTIONX_CENTER_BASE = "http://promotionx.ptdanlu.com";
        final String COUPON_NAME = "耀武赠送红包-" + (RandomUtils.nextInt(1000));
        final String ACTIVITY_NAME = "耀武赠送红包-" + (RandomUtils.nextInt(1000));
        final int COUPON_AMOUNT = 500 + RandomUtils.nextInt(5000);
        final String COUPON_CREATE_PERSON = "c5f20a6943e6414181ee1b0ce9180c6d";
        final String COUPON_RECEIVE_PERSON_LIST = "acd2031e987e44b09b4f8a033d943c81";
        final int COUPON_NUM = 1;

        // create coupon
        JSONObject jsonObject1 = new JSONObject();
        jsonObject1.put("couponName", COUPON_NAME);
        jsonObject1.put("couponTypeId", "01");
        jsonObject1.put("validDay", String.valueOf(3 + RandomUtils.nextInt(15)));
        List<Object> amoutsList = new ArrayList<>();
        JSONObject amouts = new JSONObject();
        amouts.put("amt", COUPON_AMOUNT);
        amouts.put("amount", COUPON_NUM);
        amoutsList.add(amouts);
        jsonObject1.put("amoutsList", amoutsList);
        jsonObject1.put("createPerson", COUPON_CREATE_PERSON);
        jsonObject1.put("createPersonName", "system");

        // call
        ResponseEntity<Map<String, Object>> responseEntity1 = tcHttpxService.postJson4Entity(
                PROMOTIONX_CENTER_BASE + "/coupon/create",
                null,
                null,
                jsonObject1,
                new ParameterizedTypeReference<Map<String, Object>>() {
                }
        );
    }

}