package com.ysu.zyw.tc.components.aliyun.oss;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.ObjectMetadata;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import javax.annotation.Nonnull;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Objects;

import static com.google.common.base.Preconditions.checkNotNull;

@Slf4j
public class TcAliyunOssService implements InitializingBean, DisposableBean {

    @Getter
    @Setter
    public String domainName;

    @Setter
    private String endpoint;

    @Setter
    private String accessKeyId;

    @Setter
    private String accessKeySecret;

    @Getter
    private OSSClient ossClient;

    public void set(@Nonnull String bucketName, @Nonnull String folder) {
        checkNotNull(bucketName);
        checkNotNull(folder);

        String keySuffixWithSlash = formatKey(folder);

        ossClient.putObject(bucketName, keySuffixWithSlash, new ByteArrayInputStream(new byte[0]));
    }

    public void set(@Nonnull String bucketName, @Nonnull String folder, @Nonnull String name,
                    @Nonnull InputStream inputStream) {
        checkNotNull(bucketName);
        checkNotNull(folder);
        checkNotNull(name);
        checkNotNull(inputStream);

        String key = formatKey(folder, name);

        ossClient.putObject(bucketName, key, inputStream);
    }

    public void set(@Nonnull String bucketName, @Nonnull String folder, @Nonnull String name,
                    @Nonnull InputStream inputStream, @Nonnull ObjectMetadata objectMetadata) {
        checkNotNull(bucketName);
        checkNotNull(folder);
        checkNotNull(name);
        checkNotNull(inputStream);
        checkNotNull(objectMetadata);

        String key = formatKey(folder, name);

        ossClient.putObject(bucketName, key, inputStream, objectMetadata);
    }

    public boolean exists(@Nonnull String bucketName, @Nonnull String folder) {
        checkNotNull(bucketName);
        checkNotNull(folder);

        String key = formatKey(folder);

        return ossClient.doesObjectExist(bucketName, key);
    }

    public boolean exists(@Nonnull String bucketName, @Nonnull String folder, @Nonnull String name) {
        checkNotNull(bucketName);
        checkNotNull(folder);
        checkNotNull(name);

        String key = formatKey(folder, name);

        return ossClient.doesObjectExist(bucketName, key);
    }

    public InputStream get(@Nonnull String bucketName, @Nonnull String folder, @Nonnull String name) {
        checkNotNull(bucketName);
        checkNotNull(folder);
        checkNotNull(name);

        String key = formatKey(folder, name);

        OSSObject object = ossClient.getObject(bucketName, key);

        return object.getObjectContent();
    }

    public void del(@Nonnull String bucketName, @Nonnull String folder) {
        checkNotNull(bucketName);
        checkNotNull(folder);

        String keySuffixWithSlash = formatKey(folder);

        ossClient.deleteObject(bucketName, keySuffixWithSlash);
    }

    public void del(@Nonnull String bucketName, @Nonnull String folder, @Nonnull String name) {
        checkNotNull(bucketName);
        checkNotNull(folder);
        checkNotNull(name);

        String key = formatKey(folder, name);

        ossClient.deleteObject(bucketName, key);
    }

    private String formatKey(@Nonnull String folder) {
        boolean validFolderName = folder.startsWith("/") && !folder.endsWith("/");

        if (!validFolderName) {
            throw new IllegalArgumentException("invalid folder name");
        }

        return folder.substring(1, folder.length()) + "/";
    }

    private String formatKey(@Nonnull String folder, @Nonnull String name) {
        checkNotNull(folder);
        checkNotNull(name);

        boolean validFolderName = folder.startsWith("/") && !folder.endsWith("/");
        boolean validName = !name.startsWith("/");

        if (!validFolderName || !validName) {
            throw new IllegalArgumentException("invalid folder name or invalid name");
        }

        return folder.substring(1, folder.length()) + "/" + name;
    }

    public String getObjectUrl(String domain, String name) {
        boolean validDomain = domain.startsWith("http") && !domain.endsWith("/");
        boolean validName = !name.startsWith("/");

        if (!validDomain || !validName) {
            throw new IllegalArgumentException("invalid domain name or invalid name");
        }

        return domain + "/" + name;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        checkNotNull(endpoint);
        checkNotNull(accessKeyId);
        checkNotNull(accessKeySecret);
        ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        log.info("successful initializing oss client ...");
    }

    @Override
    public void destroy() throws Exception {
        if (Objects.nonNull(ossClient)) {
            ossClient.shutdown();
            log.info("successful destroy oss client ...");
        }
    }

}
