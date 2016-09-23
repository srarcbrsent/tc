package com.ysu.zyw.tc.components.validate.factory;

import com.ysu.zyw.tc.components.validate.TcValidation;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;
import java.util.regex.Pattern;

public class TcStringValidationBuilder extends TcBaseValidationBuilder<String> {

    @Getter
    @Setter
    private int minLength = 0;

    @Getter
    @Setter
    private int maxLength = Integer.MAX_VALUE;

    @Getter
    @Setter
    private Pattern pattern;

    public TcStringValidationBuilder(String actualValue) {
        super(actualValue);
    }

    public TcStringValidationBuilder range(int min, int max) {
        this.minLength = min;
        this.maxLength = max;
        return this;
    }

    public TcStringValidationBuilder matches(Pattern pattern) {
        this.pattern = pattern;
        return this;
    }

    @Override
    public TcValidation<String> verify() {
        if (!Objects.isNull(this.getNotNull()) && Objects.equals(this.getNotNull(), false)) {
            this.
        }
        return null;
    }

}
