package com.ysu.zyw.tc.model.api.account;

import com.ysu.zyw.tc.model.api.menum.TmSignupPlatform;
import com.ysu.zyw.tc.model.validator.constraints.Mobile;
import com.ysu.zyw.tc.model.validator.constraints.SafeString;
import com.ysu.zyw.tc.model.validator.mode.TcCreateMode;
import com.ysu.zyw.tc.model.validator.mode.TcUpdateMode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.Date;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@ApiModel
public class TmAccount implements Serializable {

    @ApiModelProperty(value = "账号id")
    @Null(groups = TcCreateMode.class,
            message = "创建账号时必须不传递参数【账号id】")
    @NotNull(groups = TcUpdateMode.class,
            message = "更新账号时必须传递参数【账号id】")
    @Length(min =
            32, max = 32,
            groups = TcUpdateMode.class,
            message = "【账号id】限制为32个字符")
    private String id;

    @ApiModelProperty(value = "企业名")
    @NotNull(groups = TcCreateMode.class,
            message = "创建账号时必须传递参数【企业名】")
    @SafeString(min = 6,
            max = 48,
            oneChineseAs2Char = true,
            groups = {TcCreateMode.class, TcUpdateMode.class},
            message = "【企业名】限制为6-48个字符（一个中文计为两个字符）")
    private String name;

    @ApiModelProperty(value = "账号")
    @Pattern(regexp = "^[a-zA-Z][a-zA-Z0-9-_]{5,15}$",
            groups = {TcCreateMode.class, TcUpdateMode.class},
            message = "【账号】限制为6-16个英文开头的字符或数字")
    private String account;

    @ApiModelProperty(value = "邮箱")
    @Email(groups = {TcCreateMode.class, TcUpdateMode.class},
            message = "【邮箱】格式不合法")
    private String email;

    @ApiModelProperty(value = "手机")
    @Mobile(groups = {TcCreateMode.class, TcUpdateMode.class},
            message = "【手机】格式不合法")
    private String mobile;

    @ApiModelProperty(value = "密码")
    @NotNull(groups = TcCreateMode.class,
            message = "创建账号时必须传递参数【密码】")
    @Length(min = 64, max = 64,
            groups = TcCreateMode.class,
            message = "【密码】限制为64个字符")
    @Null(groups = TcUpdateMode.class,
            message = "更新账号时必须不传递参数【密码】")
    private String password;

    @ApiModelProperty(value = "自我描述")
    @Length(min = 12,
            max = 120,
            groups = {TcCreateMode.class, TcUpdateMode.class},
            message = "【自我描述】限制为12-120个字符")
    private String selfDescribing;

    @ApiModelProperty(value = "解锁时间", example = "yyyy-MM-dd HH:mm:ss.SSS")
    @Null(groups = {TcCreateMode.class, TcUpdateMode.class},
            message = "【解锁时间】不允许设置")
    private Date lockReleaseTime;

    @ApiModelProperty(value = "注册渠道")
    @NotNull(groups = TcCreateMode.class,
            message = "创建账号时必须传递参数【注册渠道】")
    @Null(groups = TcUpdateMode.class,
            message = "更新账号时必须不传递参数【注册渠道】")
    private TmSignupPlatform signupPlatform;

    @ApiModelProperty(value = "注册时间", example = "yyyy-MM-dd HH:mm:ss.SSS")
    @Null(groups = {TcCreateMode.class, TcUpdateMode.class},
            message = "【注册时间】不允许设置")
    private Date signupTimestamp;

    @ApiModelProperty(value = "手机已激活")
    @NotNull(groups = TcCreateMode.class,
            message = "创建账号时必须传递参数【手机已激活】")
    @Null(groups = TcUpdateMode.class,
            message = "更新账号时必须不传递参数【手机已激活】")
    private Boolean mobileActivated;

    @ApiModelProperty(value = "邮箱已激活")
    @NotNull(groups = TcCreateMode.class,
            message = "创建账号时必须传递参数【邮箱已激活】")
    @Null(groups = TcUpdateMode.class,
            message = "更新账号时必须不传递参数【邮箱已激活】")
    private Boolean emailActivated;

    @ApiModelProperty(value = "支持微信付款")
    @NotNull(groups = TcCreateMode.class,
            message = "创建账号时必须传递参数【支持微信付款】")
    @Null(groups = TcUpdateMode.class,
            message = "创建账号时必须传递参数【支持微信付款】")
    private Boolean supWeixin;

    @ApiModelProperty(value = "支持支付宝付款")
    @NotNull(groups = TcCreateMode.class,
            message = "创建账号时必须传递参数【支持支付宝付款】")
    @Null(groups = TcUpdateMode.class,
            message = "创建账号时必须传递参数【支持支付宝付款】")
    private Boolean supZhifubao;

    @ApiModelProperty(value = "支持货到付款")
    @NotNull(groups = TcCreateMode.class,
            message = "创建账号时必须传递参数【支持货到付款】")
    @Null(groups = TcUpdateMode.class,
            message = "创建账号时必须传递参数【支持货到付款】")
    private Boolean supCod;

    @ApiModelProperty(value = "微信账号")
    @Length(min = 6,
            max = 32,
            groups = {TcCreateMode.class, TcUpdateMode.class},
            message = "【微信账号】限制为6-32个字符")
    private String weixinAccount;

    @ApiModelProperty(value = "支付宝账号")
    @Length(min = 6,
            max = 32,
            groups = {TcCreateMode.class, TcUpdateMode.class},
            message = "【支付宝账号】限制为6-32个字符")
    private String zhifubaoAccount;

}
