package com.ysu.zyw.tc.components.commons.logger;

public class TcAbstractExtensionLogger implements TcExtensionLogger {

    @Override
    public boolean isTraceEnabled() {
        return false;
    }

    @Override
    public void trace(Class<?> clazz, String msg) {

    }

    @Override
    public void trace(Class<?> clazz, String format, Object... arguments) {

    }

    @Override
    public void trace(Class<?> clazz, String msg, Throwable t) {

    }

    @Override
    public boolean isDebugEnabled() {
        return false;
    }

    @Override
    public void debug(Class<?> clazz, String msg) {

    }

    @Override
    public void debug(Class<?> clazz, String format, Object... arguments) {

    }

    @Override
    public void debug(Class<?> clazz, String msg, Throwable t) {

    }

    @Override
    public boolean isInfoEnabled() {
        return false;
    }

    @Override
    public void info(Class<?> clazz, String msg) {

    }

    @Override
    public void info(Class<?> clazz, String format, Object... arguments) {

    }

    @Override
    public void info(Class<?> clazz, String msg, Throwable t) {

    }

    @Override
    public boolean isWarnEnabled() {
        return false;
    }

    @Override
    public void warn(Class<?> clazz, String msg) {

    }

    @Override
    public void warn(Class<?> clazz, String format, Object... arguments) {

    }

    @Override
    public void warn(Class<?> clazz, String msg, Throwable t) {

    }

    @Override
    public boolean isErrorEnabled() {
        return false;
    }

    @Override
    public void error(Class<?> clazz, String msg) {

    }

    @Override
    public void error(Class<?> clazz, String format, Object... arguments) {

    }

    @Override
    public void error(Class<?> clazz, String msg, Throwable t) {

    }

}
