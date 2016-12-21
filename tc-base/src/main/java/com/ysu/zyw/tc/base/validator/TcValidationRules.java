package com.ysu.zyw.tc.base.validator;

import com.google.common.collect.Lists;
import com.ysu.zyw.tc.base.tools.TcTristate;
import com.ysu.zyw.tc.base.tools.TcTuple;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Objects;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * TODO
 * 条件规则 如果有值则必须符合条件 如果无值则无值 如果有子集合则必须符合条件 如果没有则忽略
 */
public class TcValidationRules {

    private boolean shortCircuit = true;

    private List<TcTuple<TcRule, String>> rules = Lists.newArrayList();

    private TcTristate verifyPass = TcTristate.NONE;

    private List<String> errors = Lists.newArrayList();

    public static TcValidationRules newShortCircuitInstance() {
        TcValidationRules tcValidationRules = new TcValidationRules();
        tcValidationRules.shortCircuit = true;
        return tcValidationRules;
    }

    public static TcValidationRules newInstance() {
        TcValidationRules tcValidationRules = new TcValidationRules();
        tcValidationRules.shortCircuit = false;
        return tcValidationRules;
    }

    public TcValidationRules rule(@Nonnull TcRule rule,
                                  @Nonnull String error) {
        checkNotNull(rule);
        checkNotNull(error);
        rules.add(new TcTuple<>(rule, error));
        return this;
    }

    public boolean doValid() {
        if (Objects.equals(verifyPass, TcTristate.NONE)) {
            for (TcTuple<TcRule, String> tuple : rules) {
                boolean verifyPass = tuple.getFirstValue().valid();
                if (!verifyPass) {
                    errors.add(tuple.getSecondValue());
                    if (shortCircuit) {
                        break;
                    }
                }
            }
            verifyPass = errors.size() == 0 ? TcTristate.TRUE : TcTristate.FALSE;
        }
        return errors.size() == 0;
    }

    public String getError() {
        checkArgument(shortCircuit && Objects.equals(TcTristate.FALSE, verifyPass),
                "only verify failed and short circuit instance can call this api");
        return errors.get(0);
    }

    public List<String> getResultList() {
        checkArgument(!shortCircuit && Objects.equals(TcTristate.FALSE, verifyPass),
                "only verify failed and not short circuit instance can call this api");
        return errors;
    }


}
