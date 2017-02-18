package com.ysu.zyw.tc.components.commons.logger;

import com.google.common.base.Throwables;
import com.ysu.zyw.tc.base.utils.TcFormatUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;
import java.util.Objects;

import static com.google.common.base.Preconditions.checkNotNull;

public abstract class TcAbstractExtensionLogger implements TcExtensionLogger {

    private static final String EXTENSION_LOGGER_LEVEL_ENV_VARIABLE = "extensionLoggerLevel";

    private static boolean traceEnabled = false;

    private static boolean debugEnabled = false;

    private static boolean infoEnabled = false;

    private static boolean warnEnabled = false;

    private static boolean errorEnabled = false;

    static {
        String extensionLoggerLevel = System.getProperty(EXTENSION_LOGGER_LEVEL_ENV_VARIABLE);
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
    public boolean isDebugEnabled() {
        return debugEnabled;
    }

    @Override
    public boolean isInfoEnabled() {
        return infoEnabled;
    }

    @Override
    public boolean isWarnEnabled() {
        return warnEnabled;
    }

    @Override
    public boolean isErrorEnabled() {
        return errorEnabled;
    }

    @Data
    @Accessors(chain = true)
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TcExtensionLog {

        private int id;

        private String level;

        private Date date;

        private String logger;

        private String thread;

        private String file;

        private int line;

        private String msg;

        private String exception;

        private String extension;

    }

    protected TcExtensionLog convert(TcLogLevelEnum tcLogLevelEnum,
                                     Class<?> clazz,
                                     String format,
                                     Object... arguments) {
        Thread currentThread = Thread.currentThread();
        StackTraceElement ste = currentThread.getStackTrace()[1];
        // the last arguments may be a throwable
        boolean isLastArgumentInstanceOfThrowable = ArrayUtils.isNotEmpty(arguments)
                && arguments[arguments.length - 1] instanceof Throwable;
        Throwable t = isLastArgumentInstanceOfThrowable ? (Throwable) arguments[arguments.length - 1] : null;
        return new TcExtensionLog()
                .setLevel(tcLogLevelEnum.name())
                .setDate(new Date())
                .setLogger(clazz.toString())
                .setThread(currentThread.getName())
                .setFile(tryGetFilename(ste))
                .setLine(tryGetLineNumber(ste))
                // the last arguments may be a throwable
                .setMsg(tryFormatMsg(format, arguments))
                .setException(tryFormatException(t));
    }

    protected TcExtensionLog convert(TcLogLevelEnum tcLogLevelEnum,
                                     Class<?> clazz,
                                     String msg,
                                     Throwable t) {
        Thread currentThread = Thread.currentThread();
        StackTraceElement ste = currentThread.getStackTrace()[1];
        return new TcExtensionLog()
                .setLevel(tcLogLevelEnum.name())
                .setDate(new Date())
                .setLogger(clazz.toString())
                .setThread(currentThread.getName())
                .setFile(tryGetFilename(ste))
                .setLine(tryGetLineNumber(ste))
                .setMsg(msg)
                .setException(tryFormatException(t));
    }

    protected String tryGetFilename(StackTraceElement ste) {
        return ste.getFileName();
    }

    protected int tryGetLineNumber(StackTraceElement ste) {
        return ste.getLineNumber();
    }

    protected String tryFormatMsg(String format, Object... arguments) {
        if (ArrayUtils.isNotEmpty(arguments)) {
            return TcFormatUtils.format(format, arguments);
        } else {
            return format;
        }
    }

    protected String tryFormatException(Throwable t) {
        return Objects.isNull(t)
                ? null : t.getMessage() + "\n" + Throwables.getStackTraceAsString(Throwables.getRootCause(t));
    }

}
