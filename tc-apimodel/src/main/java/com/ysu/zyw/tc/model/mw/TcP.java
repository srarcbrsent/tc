package com.ysu.zyw.tc.model.mw;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@NoArgsConstructor
@ApiModel(value = "响应包裹类型", parent = TcR.class)
public class TcP<T> extends TcR<T> implements Serializable {

    private static final long serialVersionUID = -7088495956025643921L;

    @ApiModelProperty(value = "当前页")
    private int currentPage = -1;

    @ApiModelProperty(value = "总页数")
    private int totalPage = -1;

    @ApiModelProperty(value = "每页条数")
    private int pageSize = -1;

    public TcP(int code) {
        super(code);
    }

    public static <T> TcP<T> ok(T body) {
        TcP<T> tcP = new TcP<>();
        tcP.code = R.SUCCESS;
        tcP.body = body;
        return tcP;
    }

    public static <T> TcP<T> ok(T body, int currentPage, int totalPage, int pageSize) {
        TcP<T> tcP = new TcP<>();
        tcP.code = R.SUCCESS;
        tcP.body = body;
        tcP.currentPage = currentPage;
        tcP.totalPage = totalPage;
        tcP.pageSize = pageSize;
        return tcP;
    }

}
