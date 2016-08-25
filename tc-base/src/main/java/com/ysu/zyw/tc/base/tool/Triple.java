package com.ysu.zyw.tc.base.tool;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Triple impl, use for pass three object.
 *
 * @author yaowu.zhang
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Triple<K, V, T> implements Serializable {

    private K firstValue;

    private V secondValue;

    private T thirdValue;

}
