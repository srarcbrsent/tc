package com.ysu.zyw.tc.components.commons.logger;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;

import static com.google.common.base.Preconditions.checkNotNull;

public class TcAbstractExtensionLogger implements TcExtensionLogger {

    private static boolean traceEnabled = false;

    private static boolean debugEnabled = false;

    private static boolean infoEnabled = false;

    private static boolean warnEnabled = false;

    private static boolean errorEnabled = false;

    static {
        String extensionLoggerLevel = System.getProperty("extensionLoggerLevel");
        TcLogLevelEnum logLevel = TcLogLevelEnum.INFO;
        if (StringUtils.isNotEmpty(extensionLoggerLevel)) {
            try {
                logLevel = TcLogLevelEnum.valueOf(extensionLoggerLevel);
            } catch (IllegalArgumentException e) {
                // no match log level, pass to info level
                logLevel = TcLogLevelEnum.INFO;
                // no log can be applied there, sout it.
                e.printStackTrace();
            }
        }
        checkNotNull(logLevel);
        switch (logLevel) {
            case TRACE:
                traceEnabled = true;
                // no break, pass through
            case DEBUG:
                debugEnabled = true;
                // no break, pass through
            case INFO:
                infoEnabled = true;
                // no break, pass through
            case WARN:
                warnEnabled = true;
                // no break, pass through
            case ERROR:
                errorEnabled = true;
                // no break, pass through
        }
    }

    @Override
    public boolean isTraceEnabled() {
        return traceEnabled;
    }

    @Override
    public void trace(Class<?> clazz, String msg) {

    }

    @Override
    public void trace(Class<?> clazz, String format, Object... arguments) {

    }

    @Override
    public void trace(Class<?> clazz, String msg, Throwable t) {

    }

    @Override
    public boolean isDebugEnabled() {
        return debugEnabled;
    }

    @Override
    public void debug(Class<?> clazz, String msg) {

    }

    @Override
    public void debug(Class<?> clazz, String format, Object... arguments) {

    }

    @Override
    public void debug(Class<?> clazz, String msg, Throwable t) {

    }

    @Override
    public boolean isInfoEnabled() {
        return infoEnabled;
    }

    @Override
    public void info(Class<?> clazz, String msg) {

    }

    @Override
    public void info(Class<?> clazz, String format, Object... arguments) {

    }

    @Override
    public void info(Class<?> clazz, String msg, Throwable t) {

    }

    @Override
    public boolean isWarnEnabled() {
        return warnEnabled;
    }

    @Override
    public void warn(Class<?> clazz, String msg) {

    }

    @Override
    public void warn(Class<?> clazz, String format, Object... arguments) {

    }

    @Override
    public void warn(Class<?> clazz, String msg, Throwable t) {

    }

    @Override
    public boolean isErrorEnabled() {
        return errorEnabled;
    }

    @Override
    public void error(Class<?> clazz, String msg) {

    }

    @Override
    public void error(Class<?> clazz, String format, Object... arguments) {

    }

    @Override
    public void error(Class<?> clazz, String msg, Throwable t) {

    }

    @Data
    @Accessors(chain = true)
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TcExtensionLogModel {

        private String id;

        private String level;

        private Date date;

        private String thread;

        private String logger;

        private int line;

        private String msg;

        private String exception;

    }

    private void convert() {

    }

}
