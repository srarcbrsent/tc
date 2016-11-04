package com.ysu.zyw.tc.api.dao.po;

import com.ysu.zyw.tc.api.dao.penum.TcPermissionType;
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
public class TcPermission implements Serializable {
    private String id;

    private TcPermissionType type;

    private String description;

    private Integer dataPms;

    private String dataUrl;

    private String dataUrlLevel;

    private Date createdTimestamp;

    private String createdPerson;

    private Date updatedTimestamp;

    private String updatedPerson;

    private static final long serialVersionUID = 1L;
}