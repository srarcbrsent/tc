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
public class Orange {

    private String id;

    List<Tank> tanks;

    @Data
    @Accessors(chain = true)
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Tank {

        private long id;

        private Map<String, Chair> stringChairMap;

        @Data
        @Accessors(chain = true)
        @NoArgsConstructor
        @AllArgsConstructor
        public static class Chair {

            private boolean[] fourLegs;

        }

    }

}
