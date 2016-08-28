package com.ysu.zyw.tc.components.upload;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * TcUploadService provide a upload file interface.
 *
 * @author yaowu.zhang
 */
public interface TcUploadService {

    public String upload(TcUploadResourceData metadata, InputStream inputStream);

    public OutputStream download(TcUploadResourceData metadata);

    public void delete(TcUploadResourceData metadata);

    public boolean exists(TcUploadResourceData metadata);

}
