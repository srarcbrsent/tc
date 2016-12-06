package com.ysu.zyw.tc.components.utils;

import com.ysu.zyw.tc.base.utils.TcDateUtils;
import com.ysu.zyw.tc.base.utils.TcSerializationUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;

import java.util.Date;

@Slf4j
public class TcApplicationExaminer implements InitializingBean {

    @Override
    public void afterPropertiesSet() throws Exception {
        logSystemTime();
    }

    private void logSystemTime() {
        log.info("application-timezone: [{}]",
                TcSerializationUtils.objectMapper.getDateFormat().getTimeZone());
        log.info("application-time: [{}]", TcDateUtils.format(new Date()));
    }

}
