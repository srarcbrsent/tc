package com.ysu.zyw.tc.api.dao.po;

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
public class TcAccountPayment implements Serializable {
    private String id;

    private Boolean supWeixin;

    private Boolean supZhifubao;

    private Boolean supCod;

    private String weixinAccount;

    private String zhifubaoAccount;

    private String updatedPerson;

    private Date updatedTimestamp;

    private String createdPerson;

    private Date createdTimestamp;

    private static final long serialVersionUID = 1L;
}