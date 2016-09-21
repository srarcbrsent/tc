package com.ysu.zyw.tc.model.c;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class TcP<T> extends TcR<T> implements Serializable {

    private int currentPage = -1;

    private int totalPage = -1;

    private int pageSize = -1;

}
