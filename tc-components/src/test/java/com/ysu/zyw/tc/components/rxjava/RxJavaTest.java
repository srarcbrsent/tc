package com.ysu.zyw.tc.components.rxjava;


import org.junit.Test;
import rx.Observable;
import rx.internal.schedulers.ExecutorScheduler;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class RxJavaTest {

    @Test
    public void test01() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        ExecutorScheduler executorScheduler = new ExecutorScheduler(executorService);

        Observable.create(t -> {
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            t.onNext("Hello World1");
            t.onCompleted();
        })
                .subscribeOn(executorScheduler)
                .subscribe(System.out::println);

        Observable.create(t -> {
            try {
                TimeUnit.SECONDS.sleep(6);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            t.onNext("Hello World2");
            t.onCompleted();
        })
                .subscribeOn(executorScheduler)
                .subscribe(System.out::println);

        TimeUnit.SECONDS.sleep(7);
    }

}
