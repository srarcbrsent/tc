package com.ysu.zyw.tc.platform.fk.servlet;

import com.ysu.zyw.tc.base.utils.TcUtils;
import com.ysu.zyw.tc.components.utils.servlet.context.TcContextLifeCycleHandler;
import com.ysu.zyw.tc.components.utils.servlet.context.TcInvokeContextInitialized;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TcContextLifeCycleHandlerImpl implements TcContextLifeCycleHandler {

    @Getter
    @Setter
    private boolean devMode = false;

    @TcInvokeContextInitialized
    public void initTcUtils() {
        TcUtils.setDevMode(devMode);
        log.info("init tcUtils => set dev mode to [{}]", devMode);
    }

}
