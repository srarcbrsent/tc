package com.ysu.zyw.tc.base.tools;

import com.ysu.zyw.tc.sys.constant.TcConstant;

import java.util.UUID;

/**
 * TcIdWorker is a id generate tools.
 *
 * @author yaowu.zhang
 */
public class TcIdWorker {

    public static String upperCaseUuid() {
        return UUID.randomUUID().toString().replace(TcConstant.Str.HYPHEN, TcConstant.Str.EMPTY).toUpperCase();
    }

}
