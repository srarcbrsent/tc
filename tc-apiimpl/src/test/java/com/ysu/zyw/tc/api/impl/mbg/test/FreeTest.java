package com.ysu.zyw.tc.api.impl.mbg.test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;

public class FreeTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void test01() {
        String s1 = new String("1");
        String s2 = new String("1");
        String s3 = "1";
        String s4 = "1";
        System.out.println(s1 == s2);
        System.out.println(s1.intern() == s2);
        System.out.println(s1.intern() == s2.intern());
        System.out.println(s3 == s4);
        System.out.println(s1 == s3);
        System.out.println(s1 == s3.intern());
        System.out.println(s1.intern() == s3);
        System.out.println(s1.intern() == s3.intern());
    }

    @Test
    public void test02() {
        System.err.println("123123");
    }

    @Test
    public void test03() throws UnknownHostException {
        System.out.println(InetAddress.getLocalHost().getCanonicalHostName());
    }

    @Test
    public void test04() {
        System.out.println(new Date());
    }

}
