package com.ysu.zyw.tc.base.tool;

import com.ysu.zyw.tc.sys.constant.TcConstant;

import java.util.UUID;

/**
 * IdWorker is a id generate tool.
 *
 * @author yaowu.zhang
 */
public class IdWorker {

    public static String upperCaseUuid() {
        return UUID.randomUUID().toString().replace(TcConstant.Str.HYPHEN, TcConstant.Str.EMPTY).toUpperCase();
    }

}
