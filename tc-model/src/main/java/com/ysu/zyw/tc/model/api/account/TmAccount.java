package com.ysu.zyw.tc.model.api.account;

import com.ysu.zyw.tc.model.api.menum.TmSigninPlatform;
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
public class TmAccount implements Serializable {
    private String id;

    private String name;

    private String email;

    private String mobile;

    private String password;

    private TmSigninPlatform signinPlatform;

    private LocalDateTime signinTime;

    private LocalDateTime lockReleaseTime;

    private LocalDateTime createdTimestamp;

    private String createdPerson;

    private LocalDateTime updatedTimestamp;

    private String updatedPerson;
}
