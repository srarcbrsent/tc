package com.ysu.zyw.tc.components.commons.fetchrunner;

import org.springframework.beans.factory.InitializingBean;

public abstract class TcBooter<T> implements InitializingBean {

    private final TcFetchRunner<T> tcFetchRunner;

    public TcBooter(TcFetchRunner<T> tcFetchRunner) {
        this.tcFetchRunner = tcFetchRunner;
    }

    public abstract void boot();

    @Override
    public void afterPropertiesSet() throws Exception {
        boot();
    }

}
