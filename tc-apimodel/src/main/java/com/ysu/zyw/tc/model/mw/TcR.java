package com.ysu.zyw.tc.model.mw;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.type.TypeReference;
import com.ysu.zyw.tc.base.utils.TcSerializationUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.annotation.Nonnull;
import java.io.Serializable;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * 1. TcR.code == com.ysu.zyw.tc.model.mw.Rc.SUCCESS => success, have body (except TcR<Void>)
 * 2. TcR.code == other => custom code, depends on api.
 * 3. TcR.code == com.ysu.zyw.tc.model.mw.Rc.BAD_REQUEST => request param error, may contains extra.
 * 4. TcR.code == com.ysu.zyw.tc.model.mw..Rc.SERVER_ERROR => server errs, may happen in any apis.
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "响应包裹类型")
public class TcR<T> implements Serializable {

    private static final long serialVersionUID = -3742606203018432820L;

    @ApiModelProperty(value = "响应码")
    protected int code;

    @ApiModelProperty(value = "响应描述")
    protected String description;

    @ApiModelProperty(value = "响应体")
    protected T body;

    @ApiModelProperty(value = "附加信息")
    protected String extra;

    public static <T> TcR<T> ok() {
        return new TcR<T>()
                .setCode(Rc.SUCCESS)
                .setDescription(Rc.SUCCESS_DESCRIPTION);
    }

    public static <T> TcR<T> ok(@Nonnull T body) {
        checkNotNull(body);
        return new TcR<T>()
                .setCode(Rc.SUCCESS)
                .setDescription(Rc.SUCCESS_DESCRIPTION)
                .setBody(body);
    }

    public static <T> TcR<T> code(int code, @Nonnull String description) {
        checkNotNull(description);
        return new TcR<T>()
                .setCode(code)
                .setDescription(description);
    }

    public static <T> TcR<T> code(int code, @Nonnull String description, @Nonnull Object extra) {
        checkNotNull(description);
        TcR<T> tcR = code(code, description);
        return tcR.addExtra(extra);
    }

    public static <T> TcR<T> br() {
        return new TcR<T>()
                .setCode(Rc.BAD_REQUEST)
                .setDescription(Rc.BAD_REQUEST_DESCRIPTION);
    }

    public static <T> TcR<T> br(@Nonnull Object extra) {
        checkNotNull(extra);
        TcR<T> tcR = br();
        return tcR.addExtra(extra);
    }

    public static <T> TcR<T> errs() {
        return new TcR<T>()
                .setCode(Rc.SERVER_ERROR)
                .setDescription(Rc.SERVER_ERROR_DESCRIPTION);
    }

    public TcR<T> addExtra(@Nonnull Object extra) {
        checkNotNull(extra);
        if (extra instanceof String) {
            setExtra((String) extra);
        } else {
            setExtra(TcSerializationUtils.writeJson(extra));
        }
        return this;
    }

    @JsonIgnore
    public boolean isSuccess() {
        return this.code == Rc.SUCCESS;
    }

    @JsonIgnore
    public boolean isPresent() {
        return this.isSuccess() && Objects.nonNull(body);
    }

    public void ifPresent(Consumer<T> consumer) {
        if (isPresent()) {
            consumer.accept(body);
        }
    }

    @JsonIgnore
    public T get() {
        return body;
    }

    public T orElse(T another) {
        return isPresent() ? body : another;
    }

    public T orElseGet(Supplier<T> supplier) {
        return isPresent() ? body : supplier.get();
    }

    public T orElseThrow(Supplier<? extends RuntimeException> supplier) {
        if (isPresent()) {
            return body;
        } else {
            throw supplier.get();
        }
    }

    @JsonIgnore
    public <R> R getExtra(TypeReference<R> typeReference) {
        if (Objects.isNull(extra)) {
            return null;
        }
        return TcSerializationUtils.readJson(extra, typeReference);
    }

}
