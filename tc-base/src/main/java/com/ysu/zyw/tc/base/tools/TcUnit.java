package com.ysu.zyw.tc.base.tools;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * unit implements.
 *
 * @author yaowu.zhang
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class TcUnit<T> implements Serializable {

    private static final long serialVersionUID = -1455160712092699531L;

    private T value;

    public static <T> TcUnit<T> with(T value) {
        return new TcUnit<>(value);
    }

}
