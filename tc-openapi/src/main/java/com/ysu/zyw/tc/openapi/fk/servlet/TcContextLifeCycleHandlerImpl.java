package com.ysu.zyw.tc.openapi.fk.servlet;

import com.ysu.zyw.tc.components.servlet.support.TcContextLifeCycleHandler;
import com.ysu.zyw.tc.openapi.fk.config.TcConfig;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;

@Slf4j
public class TcContextLifeCycleHandlerImpl implements TcContextLifeCycleHandler {

    @Resource
    private TcConfig tcConfig;

}
