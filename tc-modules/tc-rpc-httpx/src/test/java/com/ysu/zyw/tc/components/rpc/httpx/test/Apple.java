package com.ysu.zyw.tc.components.rpc.httpx.test;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.Map;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class Apple {

    private int id;

    private String name;

    private Aple aple;

    private List<String> strings;

    private List<Aple> aples;

    private Map<String, Aple> apleMap;

    @Data
    @Accessors(chain = true)
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Aple {

        private int id;

        private Boolean helo;

        private boolean hello;


    }
}
