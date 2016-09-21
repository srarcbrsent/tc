package com.ysu.zyw.tc.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class TcR<T> implements Serializable {

    private int code;

    private String description;

    private T body;

    public TcR(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public TcR(T body) {
        this.code = R.SUCCESS;
        this.description = R.SUCCESS_DESCRIPTION;
        this.body = body;
    }

    public static abstract class R {

        public static final int SUCCESS = 0;

        public static final String SUCCESS_DESCRIPTION = "请求成功！";

        public static final int UNAUTHORIZED = 401;

        public static final String UNAUTHORIZED_DESCRIPTION = "未认证！";

        public static final int FORBIDDEN = 403;

        public static final String FORBIDDEN_DESCRIPTION = "未授权！";

        public static final int NOT_FOUND = 404;

        public static final String NOT_FOUND_DESCRIPTION = "您请求的资源不存在！";

        public static final int CONFLICT = 409;

        public static final String CONFLICT_DESCRIPTION = "冲突！";

        public static final int DEPRECATED = 415;

        public static final String DEPRECATED_DESCRIPTION = "Api已过期！";

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

}
