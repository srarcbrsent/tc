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

    @Null(groups = {TcC.class})
    @NotNull(groups = {TcU.class})
    @Id
    private String id;

    @NotNull(groups = {TcC.class})
    @Region
    private String region;

    @NotNull(groups = {TcC.class})
    @NormalStr
    private String nickname;

    @NotNull(groups = {TcC.class})
    @Email
    private String email;

    @NotNull(groups = {TcC.class})
    private Boolean emailActivated;

    @NotNull(groups = {TcC.class})
    @Mobile
    private String mobile;

    @NotNull(groups = {TcC.class})
    private Boolean mobileActivated;

    @NotNull(groups = {TcC.class})
    @URL
    private String avatar;

    @NotNull(groups = {TcC.class})
    @NormalStr(min = 32, max = 32)
    private String password;

    @NotNull(groups = {TcC.class})
    @Null(groups = {TcU.class})
    private TmPlatform signinPlatform;

    @Null(groups = {TcU.class})
    private String signinIp;

    @Null(groups = {TcU.class})
    private String operatorAccountId;

}
