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
public class TcPermissionSetMapPermission implements Serializable {
    private String id;

    private String permissionSetId;

    private String permissionId;

    private Date createdTimestamp;

    private String createdPerson;

    private String updatedTimestamp;

    private String updatedPerson;

    private static final long serialVersionUID = 1L;
}