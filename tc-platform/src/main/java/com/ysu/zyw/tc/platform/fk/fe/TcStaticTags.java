package com.ysu.zyw.tc.platform.fk.fe;

import freemarker.template.SimpleHash;

public class TcStaticTags extends SimpleHash {

    @SuppressWarnings("deprecation")
    public TcStaticTags() {
        put("script", new TcScriptTag());
        put("style", new TcStyleTag());
    }

}
