package com.ysu.zyw.tc.components.validate.factory;

import com.google.common.base.Preconditions;
import com.ysu.zyw.tc.components.validate.TcValidation;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;
import java.util.function.Function;

public abstract class TcBaseValidationBuilder<T> {

    @Getter
    @Setter
    private T actualValue;

    private Function<T, String> constraintNotNullActive;

    private Function<T, String> constraintIsNullActive;

    public TcBaseValidationBuilder(T actualValue) {
        this.actualValue = actualValue;
    }

    public abstract TcValidation<T> verify();

    public TcBaseValidationBuilder notNull(Function<T, String> desc) {
        Preconditions.checkArgument(Objects.isNull(constraintIsNullActive), "not null can not set to is null field");
        this.constraintNotNullActive = desc;
        return this;
    }

    public TcBaseValidationBuilder isNull(Function<T, String> desc) {
        Preconditions.checkArgument(Objects.isNull(constraintIsNullActive), "is null can not set to not null field");
        this.constraintNotNullActive = desc;
        return this;
    }

}
