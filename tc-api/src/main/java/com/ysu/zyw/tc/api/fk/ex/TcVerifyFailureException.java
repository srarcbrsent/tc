package com.ysu.zyw.tc.api.fk.ex;

import com.ysu.zyw.tc.model.validator.TcValidator;
import com.ysu.zyw.tc.sys.ex.TcException;
import lombok.Getter;

public class TcVerifyFailureException extends TcException {

    @Getter
    private TcValidator.TcVerifyFailure tcVerifyFailure;

    public TcVerifyFailureException(TcValidator.TcVerifyFailure tcVerifyFailure) {
        super("");
        this.tcVerifyFailure = tcVerifyFailure;
    }

}
