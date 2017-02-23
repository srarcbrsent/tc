package com.ysu.zyw.tc.components.commons.logger;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TcDbExtensionLoggerTest {

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void traceLevel() {
        System.getProperties().put("extensionLoggerLevel", "TRACE");
        TcExtensionLogger tcExtensionLogger = new TcDbExtensionLogger();
        tcExtensionLogger.debug(TcDbExtensionLoggerTest.class, "123123");
        Assert.assertTrue(tcExtensionLogger.isTraceEnabled());
        Assert.assertTrue(tcExtensionLogger.isDebugEnabled());
        Assert.assertTrue(tcExtensionLogger.isInfoEnabled());
        Assert.assertTrue(tcExtensionLogger.isWarnEnabled());
        Assert.assertTrue(tcExtensionLogger.isErrorEnabled());
    }

    @Test
    public void debugLevel() {
        System.getProperties().put("extensionLoggerLevel", "DEBUG");
        TcExtensionLogger tcExtensionLogger = new TcDbExtensionLogger();
        tcExtensionLogger.debug(TcDbExtensionLoggerTest.class, "123123");
        Assert.assertFalse(tcExtensionLogger.isTraceEnabled());
        Assert.assertTrue(tcExtensionLogger.isDebugEnabled());
        Assert.assertTrue(tcExtensionLogger.isInfoEnabled());
        Assert.assertTrue(tcExtensionLogger.isWarnEnabled());
        Assert.assertTrue(tcExtensionLogger.isErrorEnabled());
    }

    @Test
    public void infoLevel() {
        System.getProperties().put("extensionLoggerLevel", "INFO");
        TcExtensionLogger tcExtensionLogger = new TcDbExtensionLogger();
        tcExtensionLogger.debug(TcDbExtensionLoggerTest.class, "123123");
        Assert.assertFalse(tcExtensionLogger.isTraceEnabled());
        Assert.assertFalse(tcExtensionLogger.isDebugEnabled());
        Assert.assertTrue(tcExtensionLogger.isInfoEnabled());
        Assert.assertTrue(tcExtensionLogger.isWarnEnabled());
        Assert.assertTrue(tcExtensionLogger.isErrorEnabled());
    }

    @Test
    public void warnLevel() {
        System.getProperties().put("extensionLoggerLevel", "WARN");
        TcExtensionLogger tcExtensionLogger = new TcDbExtensionLogger();
        tcExtensionLogger.debug(TcDbExtensionLoggerTest.class, "123123");
        Assert.assertFalse(tcExtensionLogger.isTraceEnabled());
        Assert.assertFalse(tcExtensionLogger.isDebugEnabled());
        Assert.assertFalse(tcExtensionLogger.isInfoEnabled());
        Assert.assertTrue(tcExtensionLogger.isWarnEnabled());
        Assert.assertTrue(tcExtensionLogger.isErrorEnabled());
    }

    @Test
    public void errorLevel() {
        System.getProperties().put("extensionLoggerLevel", "ERROR");
        TcExtensionLogger tcExtensionLogger = new TcDbExtensionLogger();
        tcExtensionLogger.debug(TcDbExtensionLoggerTest.class, "123123");
        Assert.assertFalse(tcExtensionLogger.isTraceEnabled());
        Assert.assertFalse(tcExtensionLogger.isDebugEnabled());
        Assert.assertFalse(tcExtensionLogger.isInfoEnabled());
        Assert.assertFalse(tcExtensionLogger.isWarnEnabled());
        Assert.assertTrue(tcExtensionLogger.isErrorEnabled());
    }

    @Test
    public void misLevel() {
        System.getProperties().put("extensionLoggerLevel", "123123");
        TcExtensionLogger tcExtensionLogger = new TcDbExtensionLogger();
        tcExtensionLogger.debug(TcDbExtensionLoggerTest.class, "123123");
        Assert.assertFalse(tcExtensionLogger.isTraceEnabled());
        Assert.assertFalse(tcExtensionLogger.isDebugEnabled());
        Assert.assertTrue(tcExtensionLogger.isInfoEnabled());
        Assert.assertTrue(tcExtensionLogger.isWarnEnabled());
        Assert.assertTrue(tcExtensionLogger.isErrorEnabled());
    }



}
