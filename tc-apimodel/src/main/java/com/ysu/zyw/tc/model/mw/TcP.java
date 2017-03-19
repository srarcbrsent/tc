package com.ysu.zyw.tc.model.mw;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.Serializable;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * TcP.code == com.ysu.zyw.tc.model.mw.Rc.SUCCESS => success, have body (at least a empty collection)
 * TcP.code == other => custom code, depends on api.
 * TcP.code == com.ysu.zyw.tc.model.mw.Rc.BAD_REQUEST => request param error, may contains extra.
 * TcP.code == com.ysu.zyw.tc.model.mw..Rc.SERVER_ERROR => server errs, may happen in any apis.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@NoArgsConstructor
@ApiModel(value = "响应包裹类型", parent = TcR.class)
public class TcP<T> extends TcR<T> implements Serializable {

    private static final long serialVersionUID = -7088495956025643921L;

    @ApiModelProperty(value = "当前页")
    protected int currentPage = -1;

    @ApiModelProperty(value = "总页数")
    protected int totalPage = -1;

    @ApiModelProperty(value = "每页条数")
    protected int pageSize = -1;

    public static <T> TcP<T> ok(@Nonnull T body) {
        checkNotNull(body);
        TcP<T> tcP = new TcP<>();
        tcP.setCurrentPage(1)
                .setTotalPage(1)
                .setPageSize(Integer.MAX_VALUE)
                .setCode(Rc.SUCCESS)
                .setDescription(Rc.SUCCESS_DESCRIPTION)
                .setBody(body);
        return tcP;
    }

    public static <T> TcP<T> ok(@Nonnull T body, int currentPage, int pageSize, int totalPage) {
        checkNotNull(body);
        TcP<T> tcP = new TcP<>();
        tcP.setCurrentPage(currentPage)
                .setPageSize(pageSize)
                .setTotalPage(totalPage)
                .setCode(Rc.SUCCESS)
                .setDescription(Rc.SUCCESS_DESCRIPTION)
                .setBody(body);
        return tcP;
    }

    public static <T> TcP<T> code(int code, @Nonnull String description) {
        checkNotNull(description);
        TcP<T> tcP = new TcP<>();
        tcP.setCode(code)
                .setDescription(description);
        return tcP;
    }

    public static <T> TcP<T> code(int code, @Nonnull String description, @Nullable Object extra) {
        checkNotNull(description);
        TcP<T> tcP = new TcP<>();
        tcP.setCode(code)
                .setDescription(description)
                .addExtra(extra);
        return tcP;
    }

    public static <T> TcP<T> br() {
        TcP<T> tcP = new TcP<>();
        tcP.setCode(Rc.BAD_REQUEST)
                .setDescription(Rc.BAD_REQUEST_DESCRIPTION);
        return tcP;
    }

    public static <T> TcP<T> br(@Nonnull Object extra) {
        checkNotNull(extra);
        TcP<T> tcP = br();
        tcP.addExtra(extra);
        return tcP;
    }

    public static <T> TcP<T> error() {
        TcP<T> tcP = new TcP<>();
        tcP.setCode(Rc.SERVER_ERROR)
                .setDescription(Rc.SERVER_ERROR_DESCRIPTION);
        return tcP;
    }

}
