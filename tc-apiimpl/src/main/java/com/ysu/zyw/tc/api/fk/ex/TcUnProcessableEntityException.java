package com.ysu.zyw.tc.api.fk.ex;

import com.ysu.zyw.tc.model.mw.TcExtra;
import com.ysu.zyw.tc.sys.ex.TcException;
import lombok.Getter;

import static com.google.common.base.Preconditions.checkNotNull;

public class TcUnProcessableEntityException extends TcException {

    @Getter
    private TcExtra tcExtra;

    public TcUnProcessableEntityException(TcExtra tcExtra) {
        super("");
        checkNotNull(tcExtra);
        this.tcExtra = tcExtra;
    }

}
