package com.ysu.zyw.tc.base.utils;

import com.alibaba.fastjson.JSONPath;
import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TcBeanUtilsTest {

    @Data
    @Accessors(chain = true)
    @NoArgsConstructor
    @AllArgsConstructor
    public static class A {

        private int oaId;

        private Map<String, B> obMap;

        public static A newInstance() {
            HashMap<String, B> obMap = Maps.newHashMap();
            obMap.put(RandomStringUtils.randomAscii(3),
                    new B(RandomUtils.nextInt(), Lists.newArrayList(new B.C(RandomUtils.nextInt()))));
            return new A(RandomUtils.nextInt(), obMap);
        }

        @Data
        @Accessors(chain = true)
        @NoArgsConstructor
        @AllArgsConstructor
        public static class B {

            private int obId;

            private List<C> ocList;

            @Data
            @Accessors(chain = true)
            @NoArgsConstructor
            @AllArgsConstructor
            public static class C {

                private int ocId;

            }

        }

    }

    @Test
    public void copyProperties() throws Exception {
        A a = A.newInstance();
        A cp = TcBeanUtils.copyProperties(a, new A());
        Assert.assertEquals(a, cp);
    }

    @Test
    public void deepCopyProperties() throws Exception {
        Map<String, Object> objA = Maps.newHashMap();
        Map<String, Object> objB = Maps.newHashMap();
        Map<String, Object> objC = Maps.newHashMap();

        objC.put("ocId", RandomUtils.nextInt());
        List<Object> ocList = Lists.newArrayList(objC);
        objB.put("obId", RandomUtils.nextInt());
        objB.put("ocList", ocList);

        Map<String, Object> obMap = Maps.newHashMap();
        obMap.put("998", objB);

        objA.put("oaId", RandomUtils.nextInt());
        objA.put("obMap", obMap);

        A a = TcBeanUtils.deepCopyProperties(objA, new TypeReference<A>() {
        });

        JSONPath oaIdPath = JSONPath.compile("$.oaId");
        Assert.assertEquals(oaIdPath.eval(objA), oaIdPath.eval(a));

        JSONPath obIdPath = JSONPath.compile("$..obId");
        Assert.assertEquals(obIdPath.eval(objA), obIdPath.eval(a));

        JSONPath ocIdPath = JSONPath.compile("$..ocId");
        Assert.assertEquals(ocIdPath.eval(objA), ocIdPath.eval(a));
    }

}