package com.ysu.zyw.tc.model.api.account;

import com.ysu.zyw.tc.model.api.menum.TmSignupPlatform;
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

    @ApiModelProperty(value = "企业名")
    private String name;

    @ApiModelProperty(value = "账号")
    private String account;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "手机")
    private String mobile;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "自我描述")
    private String selfDescribing;

    @ApiModelProperty(value = "注册时间", example = "yyyy-MM-dd HH:mm:ss.SSS")
    private Date signinTime;

    @ApiModelProperty(value = "解锁时间", example = "yyyy-MM-dd HH:mm:ss.SSS")
    private Date lockReleaseTime;

    @ApiModelProperty(value = "注册渠道")
    private TmSignupPlatform signupPlatform;

    @ApiModelProperty(value = "注册时间", example = "yyyy-MM-dd HH:mm:ss.SSS")
    private Date signupTimestamp;

    @ApiModelProperty(value = "手机已激活")
    private Boolean mobileActivated;

    @ApiModelProperty(value = "邮箱已激活")
    private Boolean emailActivated;

    @ApiModelProperty(value = "上次登陆时间", example = "yyyy-MM-dd HH:mm:ss.SSS")
    private Date lastLoginTimestamp;

    @ApiModelProperty(value = "支持微信付款")
    private Boolean supWeixin;

    @ApiModelProperty(value = "支持支付宝付款")
    private Boolean supZhifubao;

    @ApiModelProperty(value = "支持货到付款")
    private Boolean supCod;

    @ApiModelProperty(value = "微信账号")
    private String weixinAccount;

    @ApiModelProperty(value = "支付宝账号")
    private String zhifubaoAccount;

}
