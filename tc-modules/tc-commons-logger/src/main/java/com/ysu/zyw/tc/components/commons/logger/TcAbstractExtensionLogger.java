package com.ysu.zyw.tc.components.commons.logger;

import com.google.common.base.Throwables;
import com.ysu.zyw.tc.base.ex.TcException;
import com.ysu.zyw.tc.base.utils.TcFormatUtils;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;

import java.util.Date;
import java.util.Objects;

import static com.google.common.base.Preconditions.checkNotNull;

@Slf4j
public abstract class TcAbstractExtensionLogger implements TcExtensionLogger, InitializingBean {

    private static final String EXTENSION_LOGGER_LEVEL_ENV_VARIABLE = "extensionLoggerLevel";

    private boolean traceEnabled = false;

    private boolean debugEnabled = false;

    private boolean infoEnabled = false;

    private boolean warnEnabled = false;

    private boolean errorEnabled = false;

    @Getter
    @Setter
    private TcLogLevelEnum springConfigFileLevel = TcLogLevelEnum.INFO;

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

    @Override
    public void afterPropertiesSet() throws Exception {
        String extensionLoggerLevel = System.getProperty(EXTENSION_LOGGER_LEVEL_ENV_VARIABLE);
        TcLogLevelEnum logLevel = springConfigFileLevel;
        if (StringUtils.isNotEmpty(extensionLoggerLevel)) {
            try {
                logLevel = TcLogLevelEnum.valueOf(extensionLoggerLevel);
            } catch (IllegalArgumentException e) {
                // no match log level, fast failed.
                throw new TcException("extension logger only support [trace,debug,info,warn,error] system properties",
                        e);
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
            default:
                // no default, pass through
        }
        log.info("initializing extension logger with trace-{} debug-{} info-{} warn-{} error-{}",
                traceEnabled, debugEnabled, infoEnabled, warnEnabled, errorEnabled);
    }

    @Data
    @Accessors(chain = true)
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TcExtensionLog {

        private Integer id;

        private String uniqueKey;

        private String level;

        private Date date;

        private String logger;

        private String thread;

        private String file;

        private Integer line;

        private String msg;

        private String exception;

    }

    protected TcExtensionLog convert(String uniqueKey,
                                     TcLogLevelEnum tcLogLevelEnum,
                                     Class<?> clazz,
                                     String format,
                                     Throwable t,
                                     Object... arguments) {
        StackTraceElement ste = tryGetLoggerCallerStackTraceElement();
        return new TcExtensionLog()
                // auto_increment
                .setId(null)
                .setUniqueKey(uniqueKey)
                .setLevel(tcLogLevelEnum.name())
                .setDate(new Date())
                .setLogger(clazz.toString())
                .setThread(Thread.currentThread().getName())
                .setFile(tryGetFilename(ste))
                .setLine(tryGetLineNumber(ste))
                // the last arguments may be a throwable
                .setMsg(tryFormatMsg(format, arguments))
                .setException(tryFormatException(t));
    }

    protected StackTraceElement tryGetLoggerCallerStackTraceElement() {
        return Thread.currentThread().getStackTrace()[1];
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

    protected Throwable tryGetLastArgInstanceOfThrowable(Object... arguments) {
        boolean isLastArgumentInstanceOfThrowable = ArrayUtils.isNotEmpty(arguments)
                && arguments[arguments.length - 1] instanceof Throwable;
        return isLastArgumentInstanceOfThrowable ? (Throwable) arguments[arguments.length - 1] : null;
    }

    protected Object[] tryGetArgsWithoutLastThrowable(Object... arguments) {
        boolean isLastArgumentInstanceOfThrowable = ArrayUtils.isNotEmpty(arguments)
                && arguments[arguments.length - 1] instanceof Throwable;
        return isLastArgumentInstanceOfThrowable
                ? ArrayUtils.subarray(arguments, 0, arguments.length - 1) : arguments;
    }

}
