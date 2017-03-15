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
public class TuAccount implements Serializable {

    private static final long serialVersionUID = 761530286213318282L;

    private String id;

    private String region;

    private String nickname;

    private String email;

    private Boolean emailActivated;

    private String mobile;

    private Boolean mobileActivated;

    private String avatar;

    private String updatedPerson;

}
