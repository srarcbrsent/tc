package com.ysu.zyw.tc.model.mw;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "响应包裹类型")
public class TcR<T> implements Serializable {

    @ApiModelProperty(value = "响应码")
    protected int code;

    @ApiModelProperty(value = "响应体")
    protected T body;

    @ApiModelProperty(value = "附加信息")
    protected TcExtra extra;

    public TcR(int code) {
        this.code = code;
    }

    public static <T> TcR<T> ok(T body) {
        TcR<T> tcR = new TcR<>();
        tcR.code = R.SUCCESS;
        tcR.body = body;
        return tcR;
    }

    public static <T> TcR<T> ok() {
        return ok(null);
    }

    public TcR<T> extra(String extra) {
        this.setExtra(new TcExtra().setDescription(extra));
        return this;
    }

    public static abstract class R {

        public static final int SUCCESS = 200;

        public static final String SUCCESS_DESCRIPTION = "请求成功！";

        public static final int BAD_REQUEST = 400;

        public static final String BAD_REQUEST_DESCRIPTION = "错误请求！";

        public static final int UNAUTHORIZED = 401;

        public static final String UNAUTHORIZED_DESCRIPTION = "未认证！";

        public static final int FORBIDDEN = 403;

        public static final String FORBIDDEN_DESCRIPTION = "未授权！";

        public static final int NOT_FOUND = 404;

        public static final String NOT_FOUND_DESCRIPTION = "资源不存在！";

        public static final int CONFLICT = 409;

        public static final String CONFLICT_DESCRIPTION = "冲突！";

        public static final int DEPRECATED = 415;

        public static final String DEPRECATED_DESCRIPTION = "Api已过期！";

        public static final int UNPROCESSABLE_ENTITY = 422;

        public static final String UNPROCESSABLE_ENTITY_DESCRIPTION = "无法处理的请求！";

        public static final int SERVER_ERROR = 500;

        public static final String SERVER_ERROR_DESCRIPTION = "服务器异常，请稍后再试！";

    }

    @JsonIgnore
    public boolean isSuccess() {
        return this.code == R.SUCCESS;
    }

    @JsonIgnore
    public boolean isPresent() {
        return this.isSuccess() && Objects.nonNull(body);
    }

    @JsonIgnore
    public boolean isUnProcessableEntity() {
        return this.code == R.UNPROCESSABLE_ENTITY;
    }

    public void ifPresent(Consumer<T> consumer) {
        if (this.isPresent()) {
            consumer.accept(body);
        }
    }

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

    public int orElseGetExtraIfUnProcessable(int code) {
        if (!isUnProcessableEntity()) {
            throw new RuntimeException("this is not a un processable entity response, " +
                            "only un processable entity response may have extra data!");
        }

        if (Objects.nonNull(this.extra)) {
            return this.extra.getCode();
        }

        return code;
    }

    public int orElseThrowExtraIfUnProcessable(Supplier<? extends RuntimeException> supplier) {
        if (!isUnProcessableEntity()) {
            throw new RuntimeException("this is not a un processable entity response, " +
                    "only un processable entity response may have extra data!");
        }

        if (Objects.nonNull(this.extra)) {
            return this.extra.getCode();
        }

        throw supplier.get();
    }

    // is forceUnProcessableEntity is set to true, then we think of only the code == 422
    // then this tcR have a extra field, so if the code != 422, then get the default one.
    public String orElseGetStringExtraDescription(boolean forceUnProcessableEntity, String description) {
        if ((!forceUnProcessableEntity || isUnProcessableEntity()) &&
                Objects.nonNull(this.extra) &&
                Objects.nonNull(this.extra.getDescription())) {
            return this.extra.getDescription().toString();
        }

        return description;
    }

}
