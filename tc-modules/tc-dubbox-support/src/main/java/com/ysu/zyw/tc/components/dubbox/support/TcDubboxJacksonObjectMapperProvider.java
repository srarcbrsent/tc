package com.ysu.zyw.tc.components.dubbox.support;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ysu.zyw.tc.base.utils.TcSerializationUtils;
import lombok.extern.slf4j.Slf4j;

import javax.ws.rs.ext.ContextResolver;

@Slf4j
public class TcDubboxJacksonObjectMapperProvider implements ContextResolver<ObjectMapper> {

    @Override
    public ObjectMapper getContext(Class<?> aClass) {
        log.info("start to use tc object mapper as resteasy jaxrs jackson object mapper provider");
        return TcSerializationUtils.objectMapper;
    }

}
