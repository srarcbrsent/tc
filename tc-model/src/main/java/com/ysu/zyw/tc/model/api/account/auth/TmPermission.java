package com.ysu.zyw.tc.model.api.account.auth;

import com.ysu.zyw.tc.model.api.menum.TmPermissionType;
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
public class TmPermission implements Serializable {
    private String id;

    private TmPermissionType type;

    private String description;

    private Integer dataPms;

    private LocalDateTime createdTimestamp;

    private String createdPerson;

    private LocalDateTime updatedTimestamp;

    private String updatedPerson;
}