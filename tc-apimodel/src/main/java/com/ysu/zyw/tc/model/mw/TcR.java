package com.ysu.zyw.tc.model.mw;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * 1. TcR.code == TcR.R.SUCCESS => success, have body (except TcR<Void>)
 * 2. TcR.code == TcR.R.BAD_REQUEST => bad request, can not happened in production,
 * if some required parameter not passed, may return this.
 * 3. TcR.code == TcR.R.UNAUTHORIZED => unauthorized, not impl yet.
 * 4. TcR.code == TcR.R.FORBIDDEN => forbidden, not impl yet.
 * 5. TcR.code == TcR.R.NOT_FOUND => not found, may happen in select, update, delete apis.
 * 6. TcR.code == TcR.R.CONFLICT => conflict, may happen in create, update apis.
 * 7. TcR.code == TcR.R.DEPRECATED => deprecated, not impl yet.
 * 8. TcR.code == TcR.R.UNPROCESSABLE_ENTITY => unprocessable entity,
 * special, when an api processing occurs an biz layer exception, then return this.
 * and when this returned, TcR must contains a extra field, and this field desc it,
 * and the extra.code could be used for desc several pattern, and must contains a
 * description message(string or object) in extra.description.
 * 9. TcR.code == TcR.R.SERVER_ERROR => server error, may happen in any apis.
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
@ApiModel(value = "响应包裹类型")
public class TcR<T> implements Serializable {

    private static final long serialVersionUID = -3742606203018432820L;

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

        private static final String SUCCESS_DESCRIPTION = "请求成功！";

        public static final int BAD_REQUEST = 400;

        private static final String BAD_REQUEST_DESCRIPTION = "错误请求！";

        public static final int UNAUTHORIZED = 401;

        private static final String UNAUTHORIZED_DESCRIPTION = "未认证！";

        public static final int FORBIDDEN = 403;

        private static final String FORBIDDEN_DESCRIPTION = "未授权！";

        public static final int NOT_FOUND = 404;

        private static final String NOT_FOUND_DESCRIPTION = "资源不存在！";

        public static final int CONFLICT = 409;

        private static final String CONFLICT_DESCRIPTION = "冲突！";

        public static final int DEPRECATED = 415;

        private static final String DEPRECATED_DESCRIPTION = "Api已过期！";

        public static final int UNPROCESSABLE_ENTITY = 422;

        private static final String UNPROCESSABLE_ENTITY_DESCRIPTION = "无法处理的请求！";

        public static final int SERVER_ERROR = 500;

        private static final String SERVER_ERROR_DESCRIPTION = "服务器异常，请稍后再试！";

    }

    @JsonIgnore
    public boolean isSuccess() {
        if (this.code != R.SUCCESS) {
            log();
        }
        return this.code == R.SUCCESS;
    }

    @JsonIgnore
    public boolean isUnProcessableEntity() {
        return this.code == R.UNPROCESSABLE_ENTITY;
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

    public String orElseGetStringExtra() {
        return orElseGetStringExtra(Object::toString);
    }

    public String orElseGetStringExtra(Function<Object, String> extraProcessor) {
        switch (this.code) {
            case R.SUCCESS:
                throw new RuntimeException("only not success tcr could call this");
            case R.NOT_FOUND:
                return "请求的资源不存在，请刷新后再试！";
            case R.CONFLICT:
                return "请求的资源状态发生变化，请刷新后再试！";
            case R.UNPROCESSABLE_ENTITY:
                if (Objects.nonNull(this.extra.getDescription())) {
                    return extraProcessor.apply(this.extra.getDescription());
                }
                return "请求失败，请刷新后再试！";
            case R.SERVER_ERROR:
                return "服务器异常，请稍后再试！";
            default:
                return "服务器异常，请稍后再试！";
        }
    }

    private void log() {
        log.error("call api failed, desc [{}]", this);
    }

}
