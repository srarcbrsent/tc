package com.ysu.zyw.tc.components.cache.redis.model;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.ysu.zyw.tc.base.tools.TcIdGen;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class TcCountryMdl implements Serializable {

    private static final long serialVersionUID = 131745924993372722L;

    public static TcCountryMdl newInstance() {
        List<TcProvinceMdl> provinceMdls = Lists.newArrayList();
        IntStream.range(5, 15).forEach(i -> provinceMdls.add(TcProvinceMdl.newInstance()));
        return new TcCountryMdl(
                TcIdGen.upperCaseUuid(),
                RandomStringUtils.randomAscii(RandomUtils.nextInt(5, 100)),
                RandomUtils.nextLong(5000, 100000),
                RandomUtils.nextDouble(),
                RandomUtils.nextBoolean(),
                provinceMdls);
    }

    private String id;

    private String name;

    private long peoples;

    private double round;

    private boolean open;

    private List<TcProvinceMdl> provinceMdls;

    @Data
    @Accessors(chain = true)
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TcProvinceMdl implements Serializable {

        private static final long serialVersionUID = -5847325868820526131L;

        public static TcProvinceMdl newInstance() {
            Map<String, TcCityMdl> cityMdlHashMap = Maps.newHashMap();
            IntStream.range(5, 15).forEach(i ->
                    cityMdlHashMap.put(TcIdGen.upperCaseUuid(), TcCityMdl.newInstance()));
            return new TcProvinceMdl(
                    TcIdGen.upperCaseUuid(),
                    RandomStringUtils.randomAscii(RandomUtils.nextInt(5, 100)),
                    RandomUtils.nextLong(5000, 100000),
                    RandomUtils.nextDouble(),
                    RandomUtils.nextBoolean(),
                    cityMdlHashMap
            );
        }

        private String id;

        private String name;

        private long peoples;

        private double round;

        private boolean open;

        private Map<String, TcCityMdl> cityMdlHashMap;

        @Data
        @Accessors(chain = true)
        @NoArgsConstructor
        @AllArgsConstructor
        public static class TcCityMdl implements Serializable {

            private static final long serialVersionUID = 3286047712660809826L;

            public static TcCityMdl newInstance() {
                return new TcCityMdl(
                        TcIdGen.upperCaseUuid(),
                        RandomStringUtils.randomAscii(RandomUtils.nextInt(5, 100)),
                        RandomUtils.nextLong(5000, 100000),
                        RandomUtils.nextDouble(),
                        RandomUtils.nextBoolean()
                );
            }


            private String id;

            private String name;

            private long peoples;

            private double round;

            private boolean open;

        }

    }

}
