package com.ysu.zyw.tc.base.tools;

import com.ysu.zyw.tc.base.constant.TcStrConsts;
import lombok.experimental.UtilityClass;

import java.util.UUID;

/**
 * TcIdGen defines all id generator strategy and it's implements.
 *
 * @author yaowu.zhang
 */
@UtilityClass
public class TcIdGen {

    public static String upperCaseUuid() {
        return UUID.randomUUID().toString().replace(TcStrConsts.HYPHEN, TcStrConsts.EMPTY).toUpperCase();
    }

}
