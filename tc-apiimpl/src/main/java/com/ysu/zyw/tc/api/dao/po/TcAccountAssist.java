package com.ysu.zyw.tc.api.dao.po;

import com.ysu.zyw.tc.model.menum.TmPlatform;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class TcAccountAssist {
    private String id;

    private String accountId;

    private TmPlatform signinPlatform;

    private Date signinTimestamp;

    private String signinIp;

    private TmPlatform lastSignupPlatform;

    private Date lastSignupTimestamp;

    private String lastSignupIp;

    private String updatedPerson;

    private Date updatedTimestamp;

    private String createdPerson;

    private Date createdTimestamp;
}