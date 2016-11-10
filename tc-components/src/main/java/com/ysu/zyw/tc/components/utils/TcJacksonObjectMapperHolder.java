package com.ysu.zyw.tc.components.utils;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ysu.zyw.tc.base.utils.TcDateUtils;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

public class TcJacksonObjectMapperHolder {

    public static final ObjectMapper objectMapper;

    static {
        objectMapper = Jackson2ObjectMapperBuilder
                .json()
                .simpleDateFormat(TcDateUtils.FULL_DATE_FORMAT_VALUE)
                .featuresToEnable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
                .build();
    }

}
