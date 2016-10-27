package com.ysu.zyw.tc.base.tools;

import java.io.Serializable;

/**
 * TcHook is a class use for cross function call's object transform.
 *
 * @author yaowu.zhang
 */
public class TcHook<T> implements Serializable {

    private T object;

    public TcHook() {
    }

    public TcHook(T object) {
        this.object = object;
    }

    public T getObject() {
        return object;
    }

    public void setObject(T object) {
        this.object = object;
    }

}
