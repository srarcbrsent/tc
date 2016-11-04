package com.ysu.zyw.tc.api.fk.ex;

import com.ysu.zyw.tc.sys.ex.TcException;

public class TcResourceNotFoundException extends TcException {

    public TcResourceNotFoundException(String message, Object... infos) {
        super(message, infos);
    }

    public TcResourceNotFoundException(Throwable cause, Object... infos) {
        super(cause, infos);
    }

    public TcResourceNotFoundException(String message, Throwable cause, Object... infos) {
        super(message, cause, infos);
    }

}
