package com.ysu.zyw.tc.components.commons.utils;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

public class TcImageUtilsTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void scale() throws Exception {
        for (int i = 1; i < 5; i++) {
            try (InputStream inputStream = new FileInputStream("C:\\Users\\a\\Desktop\\background.jpg")) {
                try (ByteArrayOutputStream out = TcImageUtils.scale(inputStream, i)) {
                    FileOutputStream fileOutputStream =
                            new FileOutputStream("C:\\Users\\a\\Desktop\\background-" + i + "_0.jpg");
                    out.writeTo(fileOutputStream);
                }
            }
        }
    }

    @Test
    public void scale1() throws Exception {
        for (int i = 1; i < 5; i++) {
            try (InputStream inputStream = new FileInputStream("C:\\Users\\a\\Desktop\\background.jpg")) {
                try (ByteArrayOutputStream out = TcImageUtils.scale(inputStream, i * 100, i * 130, false)) {
                    FileOutputStream fileOutputStream =
                            new FileOutputStream("C:\\Users\\a\\Desktop\\background-" + i + "_0.jpg");
                    out.writeTo(fileOutputStream);
                }
            }
        }
    }

    @Test
    public void cut() throws Exception {
        for (int i = 1; i < 5; i++) {
            try (InputStream inputStream = new FileInputStream("C:\\Users\\a\\Desktop\\background.jpg")) {
                try (ByteArrayOutputStream out = TcImageUtils.cut(inputStream, 50 * i, 50 * i, 50 * i, 50 * i)) {
                    FileOutputStream fileOutputStream =
                            new FileOutputStream("C:\\Users\\a\\Desktop\\background-" + i + "_0.jpg");
                    out.writeTo(fileOutputStream);
                }
            }
        }
    }

    @Test
    public void convert() throws Exception {
        try (InputStream inputStream = new FileInputStream("C:\\Users\\a\\Desktop\\background.jpg")) {
            try (ByteArrayOutputStream out = TcImageUtils.convert(inputStream, "gif")) {
                FileOutputStream fileOutputStream =
                        new FileOutputStream("C:\\Users\\a\\Desktop\\background-gif.gif");
                out.writeTo(fileOutputStream);
            }
        }
    }

}