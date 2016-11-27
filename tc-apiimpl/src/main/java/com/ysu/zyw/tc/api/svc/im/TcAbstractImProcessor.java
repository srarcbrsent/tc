package com.ysu.zyw.tc.api.svc.im;

import com.ysu.zyw.tc.api.dao.mappers.TcMessageMapper;
import com.ysu.zyw.tc.model.msg.TcBaseMessage;

import javax.annotation.Resource;

public abstract class TcAbstractImProcessor {

    @Resource
    private TcMessageMapper tcMessageMapper;

    public abstract void process(TcBaseMessage tcMessage);

}
