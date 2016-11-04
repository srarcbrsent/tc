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
public class TcAccount implements Serializable {
    private String id;

    private String name;

    private String account;

    private String email;

    private String mobile;

    private String password;

    private String selfDescribing;

    private Date lockReleaseTime;

    private Boolean delected;

    private Date updatedTimestamp;

    private String updatedPerson;

    private String createdPerson;

    private Date createdTimestamp;

    private static final long serialVersionUID = 1L;
}