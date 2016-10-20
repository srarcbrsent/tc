package com.ysu.zyw.tc.api.dao.penum;

import com.ysu.zyw.tc.model.api.menum.TmSignupPlatform;

public enum TcSignupPlatform {

    PC_PLATFORM,

    MOBILE_PLATFORM;

    public static TcSignupPlatform convert(TmSignupPlatform tmSignupPlatform) {
        return TcSignupPlatform.valueOf(tmSignupPlatform.name());
    }

    public static TmSignupPlatform convert(TcSignupPlatform tcSignupPlatform) {
        return TmSignupPlatform.valueOf(tcSignupPlatform.name());
    }

}
