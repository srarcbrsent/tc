package com.ysu.zyw.tc.components.commons.upload;

import javax.annotation.Nonnull;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * TcUploadService provide a upload file interface.
 *
 * @author yaowu.zhang
 */
public interface TcUploadService {

    public String upload(@Nonnull TcUploadResourceData metadata, @Nonnull InputStream inputStream);

    public OutputStream download(@Nonnull TcUploadResourceData metadata);

    public void delete(@Nonnull TcUploadResourceData metadata);

    public boolean exists(@Nonnull TcUploadResourceData metadata);

}
