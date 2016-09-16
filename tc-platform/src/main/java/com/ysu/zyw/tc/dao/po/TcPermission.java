package com.ysu.zyw.tc.dao.po;

import com.ysu.zyw.tc.dao.penum.TcPermissionType;
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
public class TcPermission implements Serializable {
    private String id;

    private TcPermissionType type;

    private String description;

    private Integer dataPms;

    private LocalDateTime createdTimestamp;

    private String createdPerson;

    private LocalDateTime updatedTimestamp;

    private String updatedPerson;

    private static final long serialVersionUID = 1L;
}