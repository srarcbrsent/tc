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
public class TcItem {
    private String id;

    private String shopId;

    private String title;

    private String description;

    private Long price;

    private Integer stock;

    private Integer salesVolume;

    private Integer favVolume;

    private Integer commentsVolume;

    private Boolean delected;

    private String updatedPerson;

    private Date updatedTimestamp;

    private String createdPerson;

    private Date createdTimestamp;
}