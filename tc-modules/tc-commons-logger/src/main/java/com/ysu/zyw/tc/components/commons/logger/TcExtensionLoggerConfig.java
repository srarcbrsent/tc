package com.ysu.zyw.tc.components.commons.logger;

import org.apache.commons.lang3.StringUtils;

import static com.google.common.base.Preconditions.checkNotNull;

public class TcExtensionLoggerConfig {

    public static final TcLogLevelEnum LOG_LEVEL;

    static {
        String extensionLoggerLevel = System.getProperty("extensionLoggerLevel");
        TcLogLevelEnum logLevel = TcLogLevelEnum.INFO;
        if (StringUtils.isNotEmpty(extensionLoggerLevel)) {
            try {
                logLevel = TcLogLevelEnum.valueOf(extensionLoggerLevel);
            } catch (IllegalArgumentException e) {
                // no match log level, pass to info level
                logLevel = TcLogLevelEnum.INFO;
            }
        }
        checkNotNull(logLevel);
        LOG_LEVEL = logLevel;
    }

}
