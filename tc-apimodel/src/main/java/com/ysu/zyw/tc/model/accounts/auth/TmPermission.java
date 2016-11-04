package com.ysu.zyw.tc.model.accounts.auth;

import com.ysu.zyw.tc.model.menum.TmPermissionType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@ApiModel
public class TmPermission implements Serializable {
    @ApiModelProperty(value = "权限id")
    private String id;

    @ApiModelProperty(value = "权限类型")
    private TmPermissionType type;

    @ApiModelProperty(value = "权限描述")
    private String description;

    @ApiModelProperty(value = "页面元素类型pms值（页面元素类型有值）")
    private Integer dataPms;

    @ApiModelProperty(value = "菜单类型url（菜单类型有值）")
    private String dataUrl;

    @ApiModelProperty(value = "菜单层级结构（菜单类型有值）")
    private String dataUrlLevel;
}