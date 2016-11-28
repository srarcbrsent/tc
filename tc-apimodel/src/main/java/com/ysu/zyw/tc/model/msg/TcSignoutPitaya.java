package com.ysu.zyw.tc.model.msg;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class TcSignoutPitaya extends TcPitaya {

    // nonnull
    public static final String MSG_KEY_SIGNOUT_ACCOUNT_ID = "msg_key_signout_account_id";

    // nonnull
    public static final String MSG_KEY_SIGNOUT_PLATFORM = "msg_key_signout_platform";

    // nonnull
    public static final String MSG_KEY_SIGNOUT_IP = "msg_key_signout_ip";

    public TcSignoutPitaya(String generatePerson) {
        super(generatePerson);
    }

}
