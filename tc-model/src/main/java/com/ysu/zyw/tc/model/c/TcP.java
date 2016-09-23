package com.ysu.zyw.tc.model.c;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@ApiModel(value = "响应包裹类型", parent = TcR.class)
public class TcP<T> extends TcR<T> implements Serializable {

    @ApiModelProperty(value = "当前页")
    private int currentPage = -1;

    @ApiModelProperty(value = "总页数")
    private int totalPage = -1;

    @ApiModelProperty(value = "每页条数")
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
