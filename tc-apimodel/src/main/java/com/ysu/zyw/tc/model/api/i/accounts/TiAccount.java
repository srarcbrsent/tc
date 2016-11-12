package com.ysu.zyw.tc.model.api.i.accounts;

import com.ysu.zyw.tc.model.menum.TmPlatform;
import com.ysu.zyw.tc.model.validator.constraints.Mobile;
import com.ysu.zyw.tc.model.validator.constraints.SafeString;
import com.ysu.zyw.tc.model.validator.mode.TcAll;
import com.ysu.zyw.tc.model.validator.mode.TcC;
import com.ysu.zyw.tc.model.validator.mode.TcU;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Pattern;
import java.io.Serializable;


@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class TiAccount implements Serializable {

    @Null(groups = TcC.class)
    @NotEmpty(groups = TcU.class)
    private String id;

    @NotEmpty(groups = TcC.class)
    @SafeString(min = 6, max = 16, oneChineseAs2Char = true, groups = TcAll.class)
    private String name;

    @Pattern(regexp = "^[a-zA-Z0-9_]{6,16}$", groups = TcAll.class)
    private String account;

    @Email(groups = TcAll.class)
    private String email;

    private Boolean emailActivated;

    @Mobile(groups = TcAll.class)
    private String mobile;

    private Boolean mobileActivated;

    @NotEmpty(groups = TcC.class)
    private String avatar;

    @Null(groups = TcU.class)
    private String password;

    @NotNull(groups = TcC.class)
    private TmPlatform signinPlatform;

    @NotEmpty(groups = TcC.class)
    private String signinIp;

    @NotEmpty(groups = TcAll.class)
    private String operatorAccountId;

}
