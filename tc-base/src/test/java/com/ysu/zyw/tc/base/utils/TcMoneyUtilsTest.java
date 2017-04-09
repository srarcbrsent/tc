package com.ysu.zyw.tc.base.utils;

import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;

public class TcMoneyUtilsTest {

    @Test
    public void test() {
        BigDecimal y1 = TcMoneyUtils.moneyF2Y(1L);
        Assert.assertEquals(new BigDecimal("0.01"), y1);
    }

}