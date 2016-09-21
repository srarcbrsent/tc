package com.ysu.zyw.tc.base.tools;

import java.io.Serializable;

/**
 * TcCr is a class use for cross function call's object transform.
 *
 * @author yaowu.zhang
 */
public class TcCr<T> implements Serializable {

    private T object;

    public TcCr() {
    }

    public TcCr(T object) {
        this.object = object;
    }

    public T getObject() {
        return object;
    }

    public void setObject(T object) {
        this.object = object;
    }

}
