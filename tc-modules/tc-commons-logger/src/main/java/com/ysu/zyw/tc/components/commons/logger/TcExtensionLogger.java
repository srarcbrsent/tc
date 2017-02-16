package com.ysu.zyw.tc.components.commons.logger;

public interface TcExtensionLogger {

    boolean isTraceEnabled();

    void trace(Class<?> clazz, String msg);

    void trace(Class<?> clazz, String format, Object... arguments);

    void trace(Class<?> clazz, String msg, Throwable t);

    boolean isDebugEnabled();

    void debug(Class<?> clazz, String msg);

    void debug(Class<?> clazz, String format, Object... arguments);

    void debug(Class<?> clazz, String msg, Throwable t);

    boolean isInfoEnabled();

    void info(Class<?> clazz, String msg);

    void info(Class<?> clazz, String format, Object... arguments);

    void info(Class<?> clazz, String msg, Throwable t);

    boolean isWarnEnabled();

    void warn(Class<?> clazz, String msg);

    void warn(Class<?> clazz, String format, Object... arguments);

    void warn(Class<?> clazz, String msg, Throwable t);

    boolean isErrorEnabled();

    void error(Class<?> clazz, String msg);

    void error(Class<?> clazz, String format, Object... arguments);

    void error(Class<?> clazz, String msg, Throwable t);

}
