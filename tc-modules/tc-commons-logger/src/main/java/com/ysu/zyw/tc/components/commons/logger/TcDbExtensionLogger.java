package com.ysu.zyw.tc.components.commons.logger;

import com.ysu.zyw.tc.base.utils.TcUtils;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TcDbExtensionLogger extends TcAbstractExtensionLogger {

    @Override
    public void trace(Class<?> clazz, String msg) {
        TcUtils.doQuietly(
                () -> doLog(null, TcLogLevelEnum.TRACE, clazz, msg, (Object[]) null)
        );
    }

    @Override
    public void trace(Class<?> clazz, String uniqueKey, String format, Object... arguments) {
        TcUtils.doQuietly(
                () -> doLog(uniqueKey, TcLogLevelEnum.TRACE, clazz, format, arguments)
        );
    }

    @Override
    public void debug(Class<?> clazz, String msg) {
        TcUtils.doQuietly(
                () -> doLog(null, TcLogLevelEnum.DEBUG, clazz, msg, (Object[]) null)
        );
    }

    @Override
    public void debug(Class<?> clazz, String uniqueKey, String format, Object... arguments) {
        TcUtils.doQuietly(
                () -> doLog(uniqueKey, TcLogLevelEnum.DEBUG, clazz, format, arguments)
        );
    }

    @Override
    public void info(Class<?> clazz, String msg) {
        TcUtils.doQuietly(
                () -> doLog(null, TcLogLevelEnum.INFO, clazz, msg, (Object[]) null)
        );
    }

    @Override
    public void info(Class<?> clazz, String uniqueKey, String format, Object... arguments) {
        TcUtils.doQuietly(
                () -> doLog(uniqueKey, TcLogLevelEnum.INFO, clazz, format, arguments)
        );
    }

    @Override
    public void warn(Class<?> clazz, String msg) {
        TcUtils.doQuietly(
                () -> doLog(null, TcLogLevelEnum.WARN, clazz, msg, (Object[]) null)
        );
    }

    @Override
    public void warn(Class<?> clazz, String uniqueKey, String format, Object... arguments) {
        TcUtils.doQuietly(
                () -> doLog(uniqueKey, TcLogLevelEnum.WARN, clazz, format, arguments)
        );
    }

    @Override
    public void error(Class<?> clazz, String msg) {
        TcUtils.doQuietly(
                () -> doLog(null, TcLogLevelEnum.ERROR, clazz, msg, (Object[]) null)
        );
    }

    @Override
    public void error(Class<?> clazz, String uniqueKey, String format, Object... arguments) {
        TcUtils.doQuietly(
                () -> doLog(uniqueKey, TcLogLevelEnum.ERROR, clazz, format, arguments)
        );
    }

    protected void doLog(String uniqueKey,
                         TcLogLevelEnum tcLogLevelEnum,
                         Class<?> clazz,
                         String format,
                         Object... arguments) {
        TcExtensionLog tcExtensionLog = convert(uniqueKey, tcLogLevelEnum, clazz, format,
                tryGetLastArgInstanceOfThrowable(arguments), tryGetArgsWithoutLastThrowable(arguments));
        log.info("extension logger log msg [{}]", tcExtensionLog);
        doInsert(tcExtensionLog);
    }

    protected void doInsert(TcExtensionLog tcExtensionLog) {

    }

}
