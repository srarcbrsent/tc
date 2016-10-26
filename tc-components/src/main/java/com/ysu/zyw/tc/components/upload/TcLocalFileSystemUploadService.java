package com.ysu.zyw.tc.components.upload;

import com.ysu.zyw.tc.sys.constant.TcConstant;
import com.ysu.zyw.tc.sys.ex.TcException;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;

import javax.annotation.Nonnull;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * TcLocalFileSystemUploadService is a TcUploadService impl, it's directly put the file into the local
 * file system, and we use nginx mapping to this location to achieve upload, it's do not support
 * distribution environment(the upload file will put to more than one machine).
 *
 * @author yaowu.zhang
 */
@Slf4j
public class TcLocalFileSystemUploadService implements TcUploadService {

    @Getter
    @Setter
    private String localFileSystemUploadBase;

    @Getter
    @Setter
    public String visitResourceBase;

    @SneakyThrows
    @Override
    public String upload(@Nonnull TcUploadResourceData metadata, @Nonnull InputStream inputStream) {
        log.info("upload file [{}]", metadata);
        validateUploadResourceData(metadata);
        mkdirIfNecessary(metadata);
        String resourceFullPath = localFileSystemUploadBase + metadata.getFolder() + metadata.getName() + metadata
                .getExtension();
        if (exists(metadata)) {
            throw new TcException("the file already exists, please delete it then upload.", metadata);
        }
        FileUtils.copyInputStreamToFile(inputStream, new File(resourceFullPath));
        return metadata.getFolder() + metadata.getName() + metadata.getExtension();
    }

    @SneakyThrows
    @Override
    public OutputStream download(@Nonnull TcUploadResourceData metadata) {
        log.info("download file [{}]", metadata);
        validateUploadResourceData(metadata);
        String resourceFullPath = localFileSystemUploadBase + metadata.getFolder() + metadata.getName() + metadata
                .getExtension();
        File file = new File(resourceFullPath);
        checkArgument(file.isFile(), "resource [" + resourceFullPath + "] is a directory");
        if (!file.exists()) {
            throw new TcException("resource [" + resourceFullPath + "] can not found");
        }
        return FileUtils.openOutputStream(file);
    }

    @SneakyThrows
    @Override
    public void delete(@Nonnull TcUploadResourceData metadata) {
        log.info("delete file [{}]", metadata);
        validateUploadResourceData(metadata);
        String resourceFullPath = localFileSystemUploadBase + metadata.getFolder() + metadata.getName() + metadata
                .getExtension();
        File file = new File(resourceFullPath);
        checkArgument(file.isFile(), "resource [" + resourceFullPath + "] is a directory");
        FileUtils.forceDelete(file);
    }

    @Override
    public boolean exists(@Nonnull TcUploadResourceData metadata) {
        validateUploadResourceData(metadata);
        String resourceFullPath = localFileSystemUploadBase + metadata.getFolder() + metadata.getName() + metadata
                .getExtension();
        File file = new File(resourceFullPath);
        return file.exists();
    }

    private void validateUploadResourceData(TcUploadResourceData metadata) {
        if (StringUtils.isNotBlank(metadata.getFolder())) {
            checkArgument(metadata.getFolder().startsWith(TcConstant.Str.SLASH), "folder must be start with /");
            checkArgument(!metadata.getFolder().endsWith(TcConstant.Str.SLASH), "folder must not end with /");
        }

        checkArgument(StringUtils.isNotBlank(metadata.getName()), "blank name is not allowed");
        checkArgument(metadata.getName().startsWith(TcConstant.Str.SLASH), "name must be start with /");

        checkArgument(StringUtils.isNotBlank(metadata.getExtension()), "blank extension is not allowed");
        checkArgument(metadata.getExtension().startsWith(TcConstant.Str.DOT), "extension must be start with .");
    }

    @SneakyThrows
    private void mkdirIfNecessary(TcUploadResourceData metadata) {
        String fullFolderPath = localFileSystemUploadBase + metadata.getFolder();
        File file = new File(fullFolderPath);
        if (!file.exists()) {
            FileUtils.forceMkdir(file);
        }
    }

}
