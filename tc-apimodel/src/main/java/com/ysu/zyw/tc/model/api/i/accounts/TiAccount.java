package com.ysu.zyw.tc.model.api.i.accounts;

import com.ysu.zyw.tc.base.validation.constraint.Id;
import com.ysu.zyw.tc.base.validation.constraint.Mobile;
import com.ysu.zyw.tc.base.validation.constraint.NormalStr;
import com.ysu.zyw.tc.base.validation.constraint.Region;
import com.ysu.zyw.tc.base.validation.group.TcC;
import com.ysu.zyw.tc.base.validation.group.TcU;
import com.ysu.zyw.tc.model.menum.TmPlatform;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.io.Serializable;


@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class TiAccount implements Serializable {

    private static final long serialVersionUID = 761530286213318282L;

    @Null(groups = {TcC.class}, message = "新增：主键不可填写")
    @NotNull(groups = {TcU.class}, message = "更新：主键必须填写")
    @Id(message = "主键信息不符合规范")
    private String id;

    @NotNull(groups = {TcC.class}, message = "新增：区域必须填写")
    @Region(message = "区域信息不符合规范")
    private String region;

    @NotNull(groups = {TcC.class}, message = "新增：昵称必须填写")
    @NormalStr(message = "昵称信息不符合规范")
    private String nickname;

    @NotNull(groups = {TcC.class}, message = "新增：邮箱必须填写")
    @Email(message = "邮箱信息不符合规范")
    private String email;

    @NotNull(groups = {TcC.class}, message = "新增：邮箱激活状态必须填写")
    private Boolean emailActivated;

    @NotNull(groups = {TcC.class}, message = "新增：电话必须填写")
    @Mobile(message = "电话信息不符合规范")
    private String mobile;

    @NotNull(groups = {TcC.class}, message = "新增：电话激活状态必须填写")
    private Boolean mobileActivated;

    @NotNull(groups = {TcC.class}, message = "新增：头像地址必须填写")
    @URL(message = "头像信息不符合规范")
    private String avatar;

    @NotNull(groups = {TcC.class}, message = "新增：密码必须填写")
    @NormalStr(min = 32, max = 32, message = "密码信息不符合规范")
    private String password;

    @NotNull(groups = {TcC.class}, message = "新增：注册区域必须填写")
    @Null(groups = {TcU.class}, message = "更新：注册区域不可填写")
    private TmPlatform signinPlatform;

    @Null(groups = {TcU.class}, message = "更新：注册IP不可填写")
    private String signinIp;

    @Null(groups = {TcU.class}, message = "更新：操作者不可填写")
    private String operatorAccountId;

}
