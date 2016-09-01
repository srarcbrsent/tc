package com.ysu.zyw.tc.base.tools;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * tuple impl, use for pass two object.
 *
 * @author yaowu.zhang
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Tuple<K, V> implements Serializable {

    private K firstValue;

    private V secondValue;

}
