package com.ysu.zyw.tc.components.commons.upload;

import lombok.SneakyThrows;

import javax.annotation.Nonnull;
import java.io.InputStream;

import static com.google.common.base.Preconditions.checkNotNull;

public class TcUploadUtils {

    @SneakyThrows
    public static int size(@Nonnull InputStream inputStream) {
        checkNotNull(inputStream);
        return inputStream.available();
    }

}
