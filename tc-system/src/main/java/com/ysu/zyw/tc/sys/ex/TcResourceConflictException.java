package com.ysu.zyw.tc.sys.ex;

public class TcResourceConflictException extends TcException {

    public TcResourceConflictException(String message, Object... infos) {
        super(message, infos);
    }

    public TcResourceConflictException(Throwable cause, Object... infos) {
        super(cause, infos);
    }

    public TcResourceConflictException(String message, Throwable cause, Object... infos) {
        super(message, cause, infos);
    }

}
