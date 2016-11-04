package com.ysu.zyw.tc.api.dao.po;

import com.ysu.zyw.tc.api.dao.penum.TcItemState;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class TcItem implements Serializable {
    private String id;

    private String name;

    private Integer price;

    private Integer stock;

    private String sellerId;

    private TcItemState state;

    private String classifyId;

    private String updatedPerson;

    private Date updatedTimestamp;

    private String createdPerson;

    private Date createdTimestamp;

    private static final long serialVersionUID = 1L;
}