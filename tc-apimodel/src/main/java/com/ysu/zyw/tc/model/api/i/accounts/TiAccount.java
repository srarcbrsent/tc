package com.ysu.zyw.tc.model.api.i.accounts;

import com.ysu.zyw.tc.model.menum.TmPlatform;
import com.ysu.zyw.tc.model.validator.constraints.Id;
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
import org.hibernate.validator.constraints.Length;
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

    private static final long serialVersionUID = 761530286213318282L;

    @Null(groups = TcC.class)
    @NotEmpty(groups = TcU.class)
    @Id(groups = TcU.class)
    private String id;

    @NotEmpty(groups = TcC.class)
    @SafeString(min = 6, max = 16, groups = TcAll.class)
    private String name;

    @NotEmpty(groups = TcC.class)
    @Pattern(regexp = "^[a-zA-Z0-9_]{6,16}$", groups = TcAll.class)
    private String account;

    @Email(groups = TcAll.class)
    private String email;

    private Boolean emailActivated;

    @Mobile(groups = TcAll.class)
    private String mobile;

    private Boolean mobileActivated;

    @NotEmpty(groups = TcC.class)
    @Pattern(regexp = "^[a-zA-Z0-9_:/]{6,120}$", groups = TcAll.class)
    private String avatar;

    @NotEmpty(groups = TcC.class)
    @Pattern(regexp = "^P[0-9]{2}-C[0-9]{3}-D[0-9]{4}$", groups = TcAll.class)
    private String region;

    @NotEmpty(groups = TcC.class)
    @Length(min = 32, max = 32, groups = TcC.class)
    @Null(groups = TcU.class)
    private String password;

    @NotNull(groups = TcC.class)
    private TmPlatform signinPlatform;

    @NotEmpty(groups = TcC.class)
    private String signinIp;

    @NotEmpty(groups = TcAll.class)
    @Id(groups = TcAll.class)
    private String operatorAccountId;

}
