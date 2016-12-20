package com.ysu.zyw.tc.base.validator;

import com.google.common.collect.Lists;
import com.ysu.zyw.tc.base.tools.TcTristate;
import com.ysu.zyw.tc.base.tools.TcTuple;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

public class TcRuleBuilder {

    private boolean shortCircuit = true;

    private List<TcTuple<Supplier<Boolean>, String>> rules = Lists.newArrayList();

    private TcTristate verifyPass = TcTristate.NONE;

    private List<String> errors = Lists.newArrayList();

    public static TcRuleBuilder newShortCircuitInstance() {
        TcRuleBuilder tcRuleBuilder = new TcRuleBuilder();
        tcRuleBuilder.shortCircuit = true;
        return tcRuleBuilder;
    }

    public static TcRuleBuilder newInstance() {
        TcRuleBuilder tcRuleBuilder = new TcRuleBuilder();
        tcRuleBuilder.shortCircuit = false;
        return tcRuleBuilder;
    }

    public TcRuleBuilder rule(@Nonnull Supplier<Boolean> rule,
                              @Nonnull String error) {
        checkNotNull(rule);
        checkNotNull(error);
        rules.add(new TcTuple<>(rule, error));
        return this;
    }

    public boolean doValid() {
        if (Objects.equals(verifyPass, TcTristate.NONE)) {
            for (TcTuple<Supplier<Boolean>, String> tuple : rules) {
                boolean verifyPass = tuple.getFirstValue().get();
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
