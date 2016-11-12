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
public class TcAccountMapPermission {
    private String id;

    private String accountId;

    private String permissionId;

    private String updatedPerson;

    private Date updatedTimestamp;

    private String createdPerson;

    private Date createdTimestamp;
}