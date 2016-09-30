package com.ysu.zyw.tc.model.api.account.auth;

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
public class TmPermissionSet implements Serializable {
    @ApiModelProperty(value = "权限集id")
    private String id;

    @ApiModelProperty(value = "权限集desc")
    private String description;
}