package com.ysu.zyw.tc.components.commons.fetchrunner;

public class TcNonSkipStrategy implements TcSkipStrategy {

    public static final TcSkipStrategy INSTANCE = new TcNonSkipStrategy();

    @Override
    public boolean shouldSkip() {
        return false;
    }

}
