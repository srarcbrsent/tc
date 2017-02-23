package com.ysu.zyw.tc.components.commons.logger;

public interface TcExtensionLogger {

    boolean isTraceEnabled();

    void trace(Class<?> clazz, String msg);

    void trace(Class<?> clazz, String uniqueKey, String format, Object... arguments);

    boolean isDebugEnabled();

    void debug(Class<?> clazz, String msg);

    void debug(Class<?> clazz, String uniqueKey, String format, Object... arguments);

    boolean isInfoEnabled();

    void info(Class<?> clazz, String msg);

    void info(Class<?> clazz, String uniqueKey, String format, Object... arguments);

    boolean isWarnEnabled();

    void warn(Class<?> clazz, String msg);

    void warn(Class<?> clazz, String uniqueKey, String format, Object... arguments);

    boolean isErrorEnabled();

    void error(Class<?> clazz, String msg);

    void error(Class<?> clazz, String uniqueKey, String format, Object... arguments);

}
