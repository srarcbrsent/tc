package com.ysu.zyw.tc.model.api.account.auth;

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
public class TmPermissionSet implements Serializable {
    private String id;

    private String description;

    private Date createdTimestamp;

    private String createdPerson;

    private Date updatedTimestamp;

    private String updatedPerson;
}