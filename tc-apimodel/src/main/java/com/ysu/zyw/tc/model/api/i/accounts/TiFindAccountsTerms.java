package com.ysu.zyw.tc.model.api.i.accounts;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class TiFindAccountsTerms implements Serializable {

    private static final long serialVersionUID = 2485593481321892713L;

    private List<String> ids;

    private String region;

    private String nickname;

    private String email;

    private Boolean emailActivated;

    private String mobile;

    private Boolean mobileActivated;

    private String randomToken;

    private Boolean locked;

}
