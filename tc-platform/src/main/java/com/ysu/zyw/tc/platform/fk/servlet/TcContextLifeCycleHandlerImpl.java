package com.ysu.zyw.tc.platform.fk.servlet;

import com.ysu.zyw.tc.components.utils.servlet.context.TcContextLifeCycleHandler;
import com.ysu.zyw.tc.platform.fk.conf.TcConfig;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;

@Slf4j
public class TcContextLifeCycleHandlerImpl implements TcContextLifeCycleHandler {

    @Resource
    private TcConfig tcConfig;

}
