package com.ysu.zyw.tc.model.msg;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class TcSignupPitaya extends TcPitaya {

    // nonnull
    public static final String MSG_KEY_SIGNUP_ACCOUNT_ID = "msg_key_signup_account_id";

    // nonnull
    public static final String MSG_KEY_SIGNUP_PLATFORM = "msg_key_signup_platform";

    // nonnull
    public static final String MSG_KEY_SIGNUP_IP = "msg_key_signup_ip";

    public TcSignupPitaya(String generatePerson) {
        super(generatePerson);
    }

}
