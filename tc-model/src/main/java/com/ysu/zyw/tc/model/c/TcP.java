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
public class TcP<T, E> extends TcR<T, E> implements Serializable {

    @ApiModelProperty(value = "当前页")
    private int currentPage = -1;

    @ApiModelProperty(value = "总页数")
    private int totalPage = -1;

    @ApiModelProperty(value = "每页条数")
    private int pageSize = -1;

    public TcP(int code, String description) {
        super(code, description);
    }

    public static <T, E> TcP<T, E> ok(T body) {
        TcP<T, E> tcP = new TcP<>();
        tcP.code = R.SUCCESS;
        tcP.description = R.SUCCESS_DESCRIPTION;
        tcP.body = body;
        return tcP;
    }

    public static <T, E> TcP<T, E> ok(T body, int currentPage, int totalPage, int pageSize) {
        TcP<T, E> tcP = new TcP<>();
        tcP.code = R.SUCCESS;
        tcP.description = R.SUCCESS_DESCRIPTION;
        tcP.body = body;
        tcP.currentPage = currentPage;
        tcP.totalPage = totalPage;
        tcP.pageSize = pageSize;
        return tcP;
    }

}
