package com.ysu.zyw.tc.model.msg;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class TcSigininMessage extends TcBaseMessage {

    // nonnull
    public static final String MSG_KEY_SIGNIN_ACCOUNT_ID = "msg_key_signin_account_id";

    // nonnull
    public static final String MSG_KEY_SIGNIN_PLATFORM = "msg_key_signin_platform";

    // nonnull
    public static final String MSG_KEY_SIGNIN_IP = "msg_key_signin_ip";

    public TcSigininMessage(String generatePerson) {
        super(generatePerson);
    }

}
