package com.ysu.zyw.tc.components.region;

import com.ysu.zyw.tc.sys.constant.TcConstant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TcProvince implements Serializable {

    private String code;

    private String name;

    private List<TcCity> tcCityList;

    public String completeName() {
        return name;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TcCity implements Serializable {

        private String code;

        private String name;

        private TcProvince tcProvince;

        private List<TcDistrict> tcDistrictList;

        public String completeName() {
            return tcProvince.completeName() + TcConstant.Str.HYPHEN + name;
        }

        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        public static class TcDistrict implements Serializable {

            private String code;

            private String name;

            private TcCity tcCity;

            public String completeName() {
                return tcCity.completeName() + TcConstant.Str.HYPHEN + name;
            }

        }

    }

}
