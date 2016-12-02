package com.ysu.zyw.tc.base.tools;

import com.ysu.zyw.tc.sys.constant.TcConstant;
import lombok.experimental.UtilityClass;

import java.util.UUID;

@UtilityClass
public class TcIdWorker {

    public static String upperCaseUuid() {
        return UUID.randomUUID().toString().replace(TcConstant.Str.HYPHEN, TcConstant.Str.EMPTY).toUpperCase();
    }

}
