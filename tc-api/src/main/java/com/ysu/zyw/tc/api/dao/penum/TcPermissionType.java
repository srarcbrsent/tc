package com.ysu.zyw.tc.api.dao.penum;

import com.ysu.zyw.tc.model.api.menum.TmPermissionType;

public enum TcPermissionType {

    MENU,

    PAGE_ELEMENT;

    public static TcPermissionType convert(TmPermissionType tmSignupPlatform) {
        return TcPermissionType.valueOf(tmSignupPlatform.name());
    }

    public static TmPermissionType convert(TcPermissionType tcSignupPlatform) {
        return TmPermissionType.valueOf(tcSignupPlatform.name());
    }

}
