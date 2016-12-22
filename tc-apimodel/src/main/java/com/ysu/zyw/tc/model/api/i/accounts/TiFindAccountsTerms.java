package com.ysu.zyw.tc.model.api.i.accounts;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.ws.rs.FormParam;
import java.io.Serializable;
import java.util.List;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TiFindAccountsTerms implements Serializable {

    private static final long serialVersionUID = 2485593481321892713L;

    @FormParam(value = "ids")
    private List<String> ids;

    @FormParam(value = "region")
    private String region;

    @FormParam(value = "nickname")
    private String nickname;

    @FormParam(value = "email")
    private String email;

    @FormParam(value = "emailActivated")
    private Boolean emailActivated;

    @FormParam(value = "mobile")
    private String mobile;

    @FormParam(value = "mobileActivated")
    private Boolean mobileActivated;

    @FormParam(value = "randomToken")
    private String randomToken;

    @FormParam(value = "locked")
    private Boolean locked;

}
