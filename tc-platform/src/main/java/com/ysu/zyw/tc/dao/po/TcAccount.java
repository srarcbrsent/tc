package com.ysu.zyw.tc.dao.po;

import com.ysu.zyw.tc.dao.penum.TcSigninPlatform;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class TcAccount implements Serializable {
    private String id;

    private String name;

    private String email;

    private String mobile;

    private String password;

    private TcSigninPlatform signinPlatform;

    private LocalDateTime signinTime;

    private LocalDateTime lockReleaseTime;

    private Boolean delected;

    private LocalDateTime createdTimestamp;

    private String createdPerson;

    private LocalDateTime updatedTimestamp;

    private String updatedPerson;

    private static final long serialVersionUID = 1L;
}