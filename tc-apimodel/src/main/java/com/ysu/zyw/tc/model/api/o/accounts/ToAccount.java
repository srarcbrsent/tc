package com.ysu.zyw.tc.model.api.o.accounts;

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
public class ToAccount implements Serializable {

    private static final long serialVersionUID = 4154288216727450793L;

    private String id;

    private String name;

    private String account;

    private String email;

    private Boolean emailActivated;

    private String mobile;

    private Boolean mobileActivated;

    private String avatar;

    private String region;

    private String randomToken;

    private Date lockReleaseTime;

    private String password;

}
