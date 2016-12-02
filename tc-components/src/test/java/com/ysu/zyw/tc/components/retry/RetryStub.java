package com.ysu.zyw.tc.components.retry;

import com.ysu.zyw.tc.base.ex.TcException;
import org.springframework.retry.annotation.Retryable;

public class RetryStub {

    private static int counter = 3;

    @Retryable(maxAttempts = 2, value = Exception.class)
    public void sayHelo1() {
        System.out.println("call ...");
        if (counter-- == 0) {
            System.out.println("helo 1");
        } else {
            throw new TcException("helo 1");
        }
    }

    @Retryable(maxAttempts = 4, value = Exception.class)
    public void sayHelo2() {
        System.out.println("call ...");
        if (counter-- == 0) {
            System.out.println("helo 2");
        } else {
            throw new TcException("helo 2");
        }
    }

    public int sayHelo3() {
        System.out.println("call ...");
        counter--;
        counter--;
        if (counter < 0) {
            System.out.println("helo 3");
        } else {
            throw new TcException("helo 3");
        }
        return 1;
    }

    public int sayHelo4() {
        System.out.println("call ...");
        if (counter-- == -5) {
            System.out.println("helo 4");
        } else {
            throw new TcException("helo 4");
        }
        return 1;
    }

}
