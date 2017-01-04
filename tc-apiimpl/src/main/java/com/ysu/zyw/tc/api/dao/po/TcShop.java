package com.ysu.zyw.tc.api.dao.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class TcShop {
    private String id;

    private String name;

    private String location;

    private Integer describingRate;

    private Integer serviceRate;

    private Integer deliveryRate;

    private Integer comprehensiveRate;

    private Boolean supportCod;

    private Boolean supportOnlinePay;

    private Boolean locked;

    private String updatedPerson;

    private Date updatedTimestamp;

    private String createdPerson;

    private Date createdTimestamp;
}