package com.ysu.zyw.tc.base.utils;

import com.google.common.base.Throwables;
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
public class TcSerializeUtil {

    public static byte[] serialize(@Nonnull Serializable value) {
        checkNotNull(value, "null serialize value is not allowed");
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
        checkNotNull(bytes, "null deserialize bytes is not allowed");
        checkNotNull(clazz, "null deserialize clazz is not allowed");
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
        checkNotNull(text, "null serialize text is not allowed");
        return text.getBytes();
    }

    public static String deserializeStr(@Nonnull byte[] bytes) {
        checkNotNull(bytes, "null deserialize bytes is not allowed");
        return new String(bytes);
    }

}
