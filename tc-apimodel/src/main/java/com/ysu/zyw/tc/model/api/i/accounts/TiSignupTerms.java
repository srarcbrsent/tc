package com.ysu.zyw.tc.model.api.i.accounts;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class TiSignupTerms implements Serializable {

    private static final long serialVersionUID = -8514070054649234453L;

    private String username;

    private Boolean canEmailLogin;

    private Boolean canMobileLogin;

}
