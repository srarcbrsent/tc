package com.ysu.zyw.tc.components.commons.utils;

import com.ysu.zyw.tc.base.tools.TcPair;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.*;

public class TcImageUtilsTest {

    private static final String SRC_PIC = "/Users/zhangyaowu/env/sources/tc/tc-modules/tc-commons-utils"
            + "/src/test/resources/imgs/61e1a61b7bd514b771e746363ec7ab9e.jpg";

    private static final String DEST_PIC = "/Users/zhangyaowu/env/sources/tc/tc-modules/tc-commons-utils"
            + "/src/test/resources/imgs/61e1a61b7bd514b771e726365ec7ab9e.jpg";

    @Before
    public void setUp() throws Exception {
        FileUtils.deleteQuietly(new File(DEST_PIC));
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void scale() throws Exception {
        try (ByteArrayOutputStream baos = TcImageUtils.scale(new FileInputStream(SRC_PIC), 0.25);
             OutputStream os = new FileOutputStream(DEST_PIC)) {
            IOUtils.write(baos.toByteArray(), os);
        }
    }

    @Test
    public void size() throws Exception {
        try (ByteArrayOutputStream baos = TcImageUtils.size(new FileInputStream(SRC_PIC), 500, 300);
             OutputStream os = new FileOutputStream(DEST_PIC)) {
            IOUtils.write(baos.toByteArray(), os);
        }
    }

    @Test
    public void crop() throws Exception {
        try (ByteArrayOutputStream baos = TcImageUtils.crop(new FileInputStream(SRC_PIC), 500, 300);
             OutputStream os = new FileOutputStream(DEST_PIC)) {
            IOUtils.write(baos.toByteArray(), os);
        }
    }

    @Test
    public void crop1() throws Exception {
        try (ByteArrayOutputStream baos = TcImageUtils.crop(new FileInputStream(SRC_PIC), 0, 0, 500, 300);
             OutputStream os = new FileOutputStream(DEST_PIC)) {
            IOUtils.write(baos.toByteArray(), os);
        }
    }

    @Test
    public void calculateMaxSizeInFixedAspectRatio() throws Exception {
        TcPair<Integer, Integer> pair1 =
                TcImageUtils.calculateMaxSizeInFixedAspectRatio(new FileInputStream(SRC_PIC), 0.5);
        Assert.assertEquals((Integer) 540, pair1.getLeft());
        Assert.assertEquals((Integer) 1080, pair1.getRight());

        TcPair<Integer, Integer> pair2 =
                TcImageUtils.calculateMaxSizeInFixedAspectRatio(new FileInputStream(SRC_PIC), 2);
        Assert.assertEquals((Integer) 1920, pair2.getLeft());
        Assert.assertEquals((Integer) 960, pair2.getRight());
    }

}