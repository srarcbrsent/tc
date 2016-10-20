package com.ysu.zyw.tc.api.mbg.test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

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

}
