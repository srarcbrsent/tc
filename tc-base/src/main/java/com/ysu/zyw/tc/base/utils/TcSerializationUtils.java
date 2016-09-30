package com.ysu.zyw.tc.base.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Throwables;
import com.ysu.zyw.tc.sys.constant.TcConstant;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Nonnull;
import java.io.*;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * SerializerUtil provide an set of operation for serialization.
 *
 * @author yaowu.zhang
 */
@Slf4j
public class TcSerializationUtils {

    private static final ObjectMapper objectMapper;

    static {
        objectMapper = new ObjectMapper();
        objectMapper.setDateFormat(TcConstant.C.FULL_DATE_FORMAT);
    }

    public static byte[] serialize(@Nonnull Serializable value) {
        checkNotNull(value, "null writeJson value is not allowed");
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(value);
            return byteArrayOutputStream.toByteArray();
        } catch (Exception e) {
            throw Throwables.propagate(e);
        }
    }

    public static <T> T deserialize(@Nonnull byte[] bytes, @Nonnull Class<T> clazz) {
        checkNotNull(bytes, "null readJson bytes is not allowed");
        checkNotNull(clazz, "null readJson clazz is not allowed");
        try {
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
            Object object = objectInputStream.readObject();
            return clazz.cast(object);
        } catch (Exception e) {
            throw Throwables.propagate(e);
        }
    }

    public static byte[] serializeStr(@Nonnull String text) {
        checkNotNull(text, "null writeJson text is not allowed");
        return text.getBytes();
    }

    public static String deserializeStr(@Nonnull byte[] bytes) {
        checkNotNull(bytes, "null readJson bytes is not allowed");
        return new String(bytes);
    }

    public static String writeJson(@Nonnull Object object) {
        checkNotNull(object, "null writeJson object is not allowed");
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw Throwables.propagate(e);
        }
    }

    public static <T> T readJson(@Nonnull String text, @Nonnull Class<T> clazz) {
        checkNotNull(text, "null readJson str is not allowed");
        checkNotNull(clazz, "null readJson clazz is not allowed");
        try {
            return objectMapper.readValue(text, clazz);
        } catch (IOException e) {
            throw Throwables.propagate(e);
        }
    }

    public static <T> T readJson(@Nonnull String text, @Nonnull TypeReference<T> typeReference) {
        checkNotNull(text, "null readJson str is not allowed");
        checkNotNull(typeReference, "null readJson type reference is not allowed");
        try {
            return objectMapper.readValue(text, typeReference);
        } catch (IOException e) {
            throw Throwables.propagate(e);
        }
    }

}
