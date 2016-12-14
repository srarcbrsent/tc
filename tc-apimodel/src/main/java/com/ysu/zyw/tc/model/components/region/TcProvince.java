package com.ysu.zyw.tc.model.components.region;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class TcProvince implements Serializable {

    private static final long serialVersionUID = -2095689793706179103L;

    private String code;

    private String name;

    private List<TcCity> tcCityList;

    public TcProvince copy() {
        return new TcProvince(this.getCode(), this.getName(), null);
    }

    @Data
    @Accessors(chain = true)
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TcCity implements Serializable {

        private static final long serialVersionUID = 8825514571603818152L;

        private String code;

        private String name;

        private TcProvince tcProvince;

        private List<TcDistrict> tcDistrictList;

        public TcCity copy() {
            return new TcCity(this.getCode(), this.getName(), null, null);
        }

        @Data
        @Accessors(chain = true)
        @NoArgsConstructor
        @AllArgsConstructor
        public static class TcDistrict implements Serializable {

            private static final long serialVersionUID = 7745630343798270459L;

            private String code;

            private String name;

            private TcCity tcCity;

            public TcDistrict copy() {
                return new TcDistrict(this.getCode(), this.getName(), null);
            }

        }

    }

}
