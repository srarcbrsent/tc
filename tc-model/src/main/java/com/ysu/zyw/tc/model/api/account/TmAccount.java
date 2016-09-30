package com.ysu.zyw.tc.model.api.account;

import com.ysu.zyw.tc.model.api.menum.TmSigninPlatform;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel
public class TmAccount implements Serializable {

    @ApiModelProperty(value = "账号id")
    private String id;

    @ApiModelProperty(value = "账号name")
    private String name;

    @ApiModelProperty(value = "账号email")
    private String email;

    @ApiModelProperty(value = "账号mobile")
    private String mobile;

    @ApiModelProperty(value = "账号password")
    private String password;

    @ApiModelProperty(value = "账号signinPlatform")
    private TmSigninPlatform signinPlatform;

    @ApiModelProperty(value = "账号signinTime", example = "yyyy-MM-dd HH:mm:ss.SSS")
    private Date signinTime;

    @ApiModelProperty(value = "账号lockReleaseTime", example = "yyyy-MM-dd HH:mm:ss.SSS")
    private Date lockReleaseTime;
}
