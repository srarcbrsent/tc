package com.ysu.zyw.tc.base.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Throwables;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Nonnull;
import java.io.IOException;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * JsonUtil provide an set of operation to json.
 *
 * @author yaowu.zhang
 */
@Slf4j
public class JsonUtil {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static String serialize(@Nonnull Object object) {
        checkNotNull(object, "null serialize object is not allowed");
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw Throwables.propagate(e);
        }
    }

    public static <T> T deserialize(@Nonnull String text, @Nonnull Class<T> clazz) {
        checkNotNull(text, "null deserialize str is not allowed");
        checkNotNull(clazz, "null deserialize clazz is not allowed");
        try {
            return objectMapper.readValue(text, clazz);
        } catch (IOException e) {
            throw Throwables.propagate(e);
        }
    }

    public static <T> T deserialize(@Nonnull String text, @Nonnull TypeReference<?> typeReference) {
        checkNotNull(text, "null deserialize str is not allowed");
        checkNotNull(typeReference, "null deserialize type reference is not allowed");
        try {
            return objectMapper.readValue(text, typeReference);
        } catch (IOException e) {
            throw Throwables.propagate(e);
        }
    }

}
