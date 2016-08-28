package com.ysu.zyw.tc.components.upload;

import com.ysu.zyw.tc.sys.ex.TcException;
import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

import static org.junit.Assert.*;

public class TcLocalFileSystemUploadServiceTest {

    private TcLocalFileSystemUploadService tcLocalFileSystemUploadService;
    private String localFileSystemUploadBase = "/home/zhangyaowu/local/storage";
    private String visitResourceBase = "http://resource.tc.com";

    @Before
    public void setUp() throws Exception {
        tcLocalFileSystemUploadService = new TcLocalFileSystemUploadService();
        tcLocalFileSystemUploadService.setLocalFileSystemUploadBase(localFileSystemUploadBase);
        tcLocalFileSystemUploadService.setVisitResourceBase(visitResourceBase);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testLocalFileSystemUploadService() throws Exception {
        upload("", "/123", ".jpg");
        upload("/mongo", "/1234", ".jpg");
        upload("/helo/world", "/1234", ".jpg");
    }

    @Test(expected = TcException.class)
    public void testUploadTwice() throws Exception {
        TcUploadResourceData metadata = new TcUploadResourceData("/helo", "/" + UUID.randomUUID().toString(), ".jpg");
        tcLocalFileSystemUploadService.upload(metadata, FileUtils.openInputStream(new File
                ("/home/zhangyaowu/图片/d74224a3200bd5fecbb3f42dc114d914.jpeg")));
        tcLocalFileSystemUploadService.upload(metadata, FileUtils.openInputStream(new File
                ("/home/zhangyaowu/图片/d74224a3200bd5fecbb3f42dc114d914.jpeg")));
    }

    private void upload(String folder, String name, String extension) throws IOException {
        TcUploadResourceData metadata = new TcUploadResourceData(folder, name, extension);
        String path = tcLocalFileSystemUploadService.upload(metadata, FileUtils.openInputStream(new File
                ("/home/zhangyaowu/图片/d74224a3200bd5fecbb3f42dc114d914.jpeg")));
        System.out.println(path);
        Assert.assertEquals(path, folder + name + extension);
        Assert.assertTrue(tcLocalFileSystemUploadService.exists(metadata));
        OutputStream downloadResource = tcLocalFileSystemUploadService.download(metadata);
        System.out.println(downloadResource);
        tcLocalFileSystemUploadService.delete(metadata);
        Assert.assertFalse(tcLocalFileSystemUploadService.exists(metadata));
    }

}