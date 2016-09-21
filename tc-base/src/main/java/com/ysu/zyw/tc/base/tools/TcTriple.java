package com.ysu.zyw.tc.base.tools;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * TcTriple impl, use for pass three object.
 *
 * @author yaowu.zhang
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TcTriple<K, V, T> implements Serializable {

    private K firstValue;

    private V secondValue;

    private T thirdValue;

}
