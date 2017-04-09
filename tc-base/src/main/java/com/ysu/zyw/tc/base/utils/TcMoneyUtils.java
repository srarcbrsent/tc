package com.ysu.zyw.tc.base.utils;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.math.NumberUtils;

import javax.annotation.Nonnull;
import java.math.BigDecimal;

import static com.google.common.base.Preconditions.checkNotNull;

@Slf4j
@UtilityClass
public class TcMoneyUtils {

    public static BigDecimal moneyF2Y(@Nonnull String fen) {
        checkNotNull(fen);
        Long lFen = NumberUtils.createLong(fen);
        return new BigDecimal(lFen).movePointLeft(2);
    }

    public static BigDecimal moneyF2Y(@Nonnull Long fen) {
        checkNotNull(fen);
        return new BigDecimal(fen).movePointLeft(2);
    }

    public static BigDecimal moneyF2Y(@Nonnull BigDecimal fen) {
        checkNotNull(fen);
        return fen.movePointLeft(2);
    }

    public static BigDecimal moneyY2F(@Nonnull String yuan) {
        checkNotNull(yuan);
        return new BigDecimal(yuan).movePointRight(2);
    }

    public static BigDecimal moneyY2F(@Nonnull BigDecimal yuan) {
        checkNotNull(yuan);
        return yuan.movePointRight(2);
    }

}
