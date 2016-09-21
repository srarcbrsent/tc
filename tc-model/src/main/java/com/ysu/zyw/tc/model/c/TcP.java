package com.ysu.zyw.tc.model.c;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
@NoArgsConstructor
public class TcP<T> extends TcR<T> implements Serializable {

    private int currentPage = -1;

    private int totalPage = -1;

    private int pageSize = -1;

    public TcP(int code, String description) {
        super(code, description);
    }

    public TcP(T body) {
        super(body);
    }

    public TcP(T body, int currentPage, int totalPage, int pageSize) {
        super(body);
        this.currentPage = currentPage;
        this.totalPage = totalPage;
        this.pageSize = pageSize;
    }

}
