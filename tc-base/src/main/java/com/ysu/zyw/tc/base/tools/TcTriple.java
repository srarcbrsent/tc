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
public class TcTriple<K, V, T> implements Serializable {

    private K firstValue;

    private V secondValue;

    private T thirdValue;

}
