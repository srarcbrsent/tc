package com.ysu.zyw.tc.api.dao.po;

import com.ysu.zyw.tc.api.dao.penum.TcSignupPlatform;
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
public class TcAccountAssist implements Serializable {
    private String id;

    private TcSignupPlatform signupPlatform;

    private Date signupTimestamp;

    private Boolean mobileActivated;

    private Boolean emailActivated;

    private String updatedPerson;

    private Date updatedTimestamp;

    private String createdPerson;

    private Date createdTimestamp;

    private static final long serialVersionUID = 1L;
}