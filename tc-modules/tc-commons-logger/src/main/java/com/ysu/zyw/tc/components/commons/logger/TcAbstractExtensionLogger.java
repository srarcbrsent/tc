package com.ysu.zyw.tc.components.commons.logger;

import com.google.common.base.Throwables;
import com.ysu.zyw.tc.base.tools.TcIdGen;
import com.ysu.zyw.tc.base.utils.TcFormatUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;
import java.util.Objects;

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

        private String clazz;

        private String thread;

        private String logger;

        private int line;

        private String msg;

        private String exception;

    }

    private TcExtensionLogModel convert(TcLogLevelEnum tcLogLevelEnum,
                                        Class<?> clazz,
                                        String format,
                                        Object... arguments) {
        Thread currentThread = Thread.currentThread();
        StackTraceElement ste = currentThread.getStackTrace()[1];
        // the last arguments may be a throwable
        boolean isLastArgumentInstanceOfThrowable = Objects.nonNull(arguments)
                && arguments.length > 0
                && arguments[arguments.length - 1] instanceof Throwable;
        Throwable t = isLastArgumentInstanceOfThrowable ? (Throwable) arguments[arguments.length - 1] : null;
        return new TcExtensionLogModel()
                .setId(TcIdGen.upperCaseUuid())
                .setLevel(tcLogLevelEnum.name())
                .setDate(new Date())
                .setClazz(clazz.toString())
                .setThread(currentThread.getName())
                .setLogger(tryGetFilename(ste))
                .setLine(tryGetLineNumber(ste))
                // the last arguments may be a throwable
                .setMsg(tryFormatMsg(format, arguments))
                .setException(tryFormatException(t));
    }

    private TcExtensionLogModel convert(TcLogLevelEnum tcLogLevelEnum,
                                        Class<?> clazz,
                                        String msg,
                                        Throwable t) {
        Thread currentThread = Thread.currentThread();
        StackTraceElement ste = currentThread.getStackTrace()[1];
        return new TcExtensionLogModel()
                .setId(TcIdGen.upperCaseUuid())
                .setLevel(tcLogLevelEnum.name())
                .setDate(new Date())
                .setClazz(clazz.toString())
                .setThread(currentThread.getName())
                .setLogger(tryGetFilename(ste))
                .setLine(tryGetLineNumber(ste))
                .setMsg(msg)
                .setException(tryFormatException(t));
    }

    private String tryGetFilename(StackTraceElement ste) {
        return ste.getFileName();
    }

    private int tryGetLineNumber(StackTraceElement ste) {
        return ste.getLineNumber();
    }

    private String tryFormatMsg(String format, Object... arguments) {
        if (Objects.nonNull(arguments) && arguments.length > 0) {
            return TcFormatUtils.format(format, arguments);
        } else {
            return format;
        }
    }

    private String tryFormatException(Throwable t) {
        if (Objects.isNull(t)) {
            return null;
        } else {
            return t.getMessage() + Throwables.getStackTraceAsString(t);
        }
    }

}
