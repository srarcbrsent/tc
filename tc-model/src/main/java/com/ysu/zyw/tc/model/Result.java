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
public class Result<T> implements Serializable {

    private int code;

    private String description;

    private T body;

    public static abstract class R {

        public static final int SUCCESS = 0;

        public static final int UNAUTHORIZED = 401;

        public static final int FORBIDDEN = 403;

        public static final int NOT_FOUND = 404;

        public static final int CONFLICT = 409;

        public static final int DEPRECATED = 415;

        public static final int SERVER_ERROR = 500;

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
