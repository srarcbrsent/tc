package com.ysu.zyw.tc.api.dao.po;

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
public class TcAccountMapPermission implements Serializable {
    private String id;

    private String accountId;

    private String permissionId;

    private LocalDateTime createdTimestamp;

    private String createdPerson;

    private LocalDateTime updatedTimestamp;

    private String updatedPerson;

    private static final long serialVersionUID = 1L;
}