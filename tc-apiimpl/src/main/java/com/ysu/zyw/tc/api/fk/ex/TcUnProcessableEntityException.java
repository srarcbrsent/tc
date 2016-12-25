package com.ysu.zyw.tc.api.fk.ex;

import com.ysu.zyw.tc.base.ex.TcException;
import com.ysu.zyw.tc.base.utils.TcFormatUtils;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.Nonnull;

import static com.google.common.base.Preconditions.checkNotNull;

public class TcUnProcessableEntityException extends TcException {

    @Getter
    private int code;

    @Getter
    private String description;

    @Getter
    @Setter
    private Object extra;

    public TcUnProcessableEntityException(int code, @Nonnull String description) {
        super(description);
        checkNotNull(description);
        this.code = code;
        this.description = description;
    }

    public TcUnProcessableEntityException(int code, @Nonnull String description, @Nonnull Object... infos) {
        super(TcFormatUtils.format(description, infos));
        checkNotNull(description);
        this.code = code;
        this.description = TcFormatUtils.format(description, infos);
    }

}
