package com.ysu.zyw.tc.model.api.o.publics;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class TcRegion implements Serializable {

    private static final long serialVersionUID = 2479179145801894893L;

    private String id;

    private String name;

    private String cityId;

    private String cityName;

    private String provinceId;

    private String provinceName;

    public String region() {
        return id;
    }

}
