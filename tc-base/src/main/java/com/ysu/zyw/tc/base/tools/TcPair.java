package com.ysu.zyw.tc.base.tools;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class TcPair<K, V> implements Serializable {

    private static final long serialVersionUID = 8868470434090722774L;

    private K left;

    private V right;

    public static <K, V> TcPair<K, V> with(K left, V right) {
        return new TcPair<>(left, right);
    }

}
