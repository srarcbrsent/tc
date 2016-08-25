package com.ysu.zyw.tc.base.tool;

import java.io.Serializable;

/**
 * Transmitter is a class use for cross function call's object transform.
 *
 * @author yaowu.zhang
 */
public class Transmitter<T> implements Serializable {

    private T object;

    public Transmitter() {
    }

    public Transmitter(T object) {
        this.object = object;
    }

    public T getObject() {
        return object;
    }

    public void setObject(T object) {
        this.object = object;
    }

}
