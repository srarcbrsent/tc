package com.ysu.zyw.tc.base.validator;

import com.ysu.zyw.tc.base.utils.TcUtils;

import java.util.function.Supplier;

@FunctionalInterface
public interface TcRule {

    boolean valid();

    default TcRule when(Supplier<Boolean> condition) {
        return TcUtils.doQuietly(condition::get, false) ? this : () -> true;
    }

    default TcRule or(TcRule tcRule) {
        return () -> this.valid() || tcRule.valid();
    }

    default TcRule and(TcRule tcRule) {
        return () -> this.valid() && tcRule.valid();
    }

}
