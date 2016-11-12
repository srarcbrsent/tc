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
public class TcRoleMapMenu {
    private String id;

    private String roleId;

    private String menuId;

    private String updatedPerson;

    private Date updatedTimestamp;

    private String createdPerson;

    private Date createdTimestamp;
}