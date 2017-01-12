package com.ysu.zyw.tc.components.commons.utils;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class TcPinyinUtilsTest {

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void toPinyin() throws Exception {
        String[] me = TcPinyinUtils.toPinyin('我');
        Assert.assertEquals(1, me.length);
        Assert.assertEquals("wo", me[0]);
    }

    @Test
    public void toPinyin1() throws Exception {
        String lls = "人生如剑";
        LinkedHashMap<Character, String[]> lifeLikeSword = TcPinyinUtils.toPinyin(lls);
        String[] pinyins = new String[]{"ren", "sheng", "ru", "jian"};
        AtomicInteger index = new AtomicInteger(0);
        lifeLikeSword.forEach((key, value) -> {
            Assert.assertEquals(lls.charAt(index.intValue()), (char) key);
            Assert.assertEquals(1, value.length);
            Assert.assertEquals(pinyins[index.intValue()], value[0]);
            index.addAndGet(1);
        });
    }

}