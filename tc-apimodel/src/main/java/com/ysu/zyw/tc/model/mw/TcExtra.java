package com.ysu.zyw.tc.model.mw;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.validation.Errors;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.google.common.base.Preconditions.checkNotNull;

@Accessors(chain = true)
public class TcExtra implements Serializable {

    @Getter
    @Setter
    private int code;

    @Setter
    private Object description;

    public TcExtra() {
        // serialization use.
    }

    public TcExtra(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public TcExtra(int code, Errors errors) {
        checkNotNull(errors);
        if (errors.hasErrors()) {
            this.description = errors.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.joining(","));
        }
    }

    public TcExtra(int code, String... errors) {
        this.description = Arrays.stream(errors).collect(Collectors.joining(","));
    }

    public Object getDescription() {
        return Objects.isNull(this.description) ? "系统异常，请稍后再试！" : this.description;
    }

}
