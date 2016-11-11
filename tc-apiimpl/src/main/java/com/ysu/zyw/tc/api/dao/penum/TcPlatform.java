package com.ysu.zyw.tc.api.dao.penum;

import com.ysu.zyw.tc.model.menum.TmPlatform;

public enum TcPlatform {

    PC_PLATFORM,

    MOBILE_PLATFORM;

    public static TcPlatform convert(TmPlatform tmPlatform) {
        return TcPlatform.valueOf(tmPlatform.name());
    }

    public static TmPlatform convert(TcPlatform tcPlatform) {
        return TmPlatform.valueOf(tcPlatform.name());
    }

}
