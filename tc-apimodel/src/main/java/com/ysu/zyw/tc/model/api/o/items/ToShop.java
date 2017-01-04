package com.ysu.zyw.tc.model.api.o.items;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class ToShop implements Serializable {

    private static final long serialVersionUID = 412198727661825781L;

    private String id;

    private String name;

    private String location;

    private Integer describingRate;

    private Integer serviceRate;

    private Integer deliveryRate;

    private Integer comprehensiveRate;

    private Boolean supportCod;

    private Boolean supportOnlinePay;

}
