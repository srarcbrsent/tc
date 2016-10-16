package com.ysu.zyw.tc.base.utils;

import java.util.Date;
import java.util.function.Supplier;

public class TcLambdaUtils {

    public static class I {

        public static long invokeWithTiming(Supplier<?> supplier) {
            long now = new Date().getTime();
            supplier.get();
            return new Date().getTime() - now;
        }

    }

}
