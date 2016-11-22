package com.ysu.zyw.tc.components.rxjava;


import com.google.common.collect.Lists;
import com.ysu.zyw.tc.base.tools.TcHook;
import lombok.SneakyThrows;
import org.junit.Before;
import org.junit.Test;
import rx.Observable;
import rx.internal.schedulers.ExecutorScheduler;

import java.util.List;
import java.util.concurrent.*;

public class RxJavaTest {

    private ExecutorService executorService;

    private ExecutorScheduler executorScheduler;

    @Before
    public void setUp() {
        executorService = Executors.newFixedThreadPool(5);
        executorScheduler = new ExecutorScheduler(executorService);
    }

    @Test
    public void test01() throws Exception {
        Observable
                .create(onSubscribe -> {
                    onSubscribe.onNext("Hello World1");
                    onSubscribe.onNext("Hello World2");
                    onSubscribe.onCompleted();
                })
                .doOnNext(System.out::println)
                .doOnNext(System.out::println)
                .doOnNext(System.out::println)
                .doOnCompleted(() -> System.out.println("complete"))
                .doOnError(Throwable::printStackTrace)
                .doOnEach(observer -> System.out.println(observer.getKind() + "-" + observer.getValue()))
                .doOnSubscribe(() -> System.out.println("subcribe"))
                .map(Object::hashCode)
                .subscribe(System.out::println);
    }

    @Test
    public void test02() {
        Observable
                .just("Hello World")
                .doOnNext(System.out::println)
                .subscribe(System.out::println, System.out::println, () -> System.out.println("1"));
    }

    @Test
    public void test03() {
        Observable
                .just(new Integer[]{1, 2})
                .doOnNext(System.out::println)
                .subscribe();
    }

    @Test
    public void test04() {
        Observable<Long> observable = Observable.defer(() -> Observable.create(t -> {
            sleep(5);
            t.onNext(System.currentTimeMillis());
        }));
        observable.subscribe(System.out::println);
        observable.subscribe(System.out::println);
        observable.subscribe(System.out::println);
        observable.subscribe(System.out::println);
    }

    @Test
    public void test05() {
        Observable
                .create(onSubscribe -> {
                    onSubscribe.onNext("H1");
                    // on.onCompleted();
                })
                .subscribe(System.out::println, System.out::println, () -> System.out.println("1"));
    }

    @Test
    public void test06() {
        Observable.interval(1, TimeUnit.SECONDS).subscribe(System.out::println);
        sleep(5000);
    }

    @Test
    public void test07() {
        Observable
                .error(new RuntimeException())
                .subscribe(System.out::println, System.out::println, () -> System.out.println("1"));
    }

    @Test
    public void test08() {
        Observable
                .empty()
                .subscribe(System.out::println, System.out::println, () -> System.out.println("1"));
    }

    @Test
    public void test09() {
        Observable
                .just("Hello World1", "Hello World2")
                .flatMap(Observable::just)
                .subscribe(System.out::println);
    }

    @Test
    public void test10() {
        Observable
                .timer(50, TimeUnit.MILLISECONDS)
                .subscribe(System.out::println);
        sleep(3000);
    }

    @Test
    public void test11() {
        Observable<String> observable = Observable.just("Hello World");
        observable.subscribe(System.out::println, System.out::println, () -> System.out.println("1"));
        observable.subscribe(System.out::println, System.out::println, () -> System.out.println("1"));
        observable.subscribe(System.out::println, System.out::println, () -> System.out.println("1"));
        observable.subscribe(System.out::println, System.out::println, () -> System.out.println("1"));
        observable.subscribe(System.out::println, System.out::println, () -> System.out.println("1"));
    }

    @Test
    public void test12() {
        Observable
                .range(1, 100)
                .buffer(5)
                .subscribe(System.out::println);
    }

    @Test
    public void test13() {
        Observable
                .create(onSubscribe -> {
                    for (int i = 0; i < 100; i++) {
                        onSubscribe.onNext(i);
                        sleep(1);
                    }
                })
                .buffer(4, TimeUnit.MILLISECONDS, 15)
                .subscribe(System.out::println);
    }

    @Test
    public void test14() {
        Observable
                .range(1, 10)
                .flatMap(item -> Observable.range(0, item))
                .subscribe(System.out::println);
    }

    @Test
    public void test15() {
        Observable
                .range(0, 100)
                .groupBy(i -> i % 10)
                .subscribe(groupedObservable -> {
                            groupedObservable.subscribe(i -> {
                                System.out.println(groupedObservable + " " + i);
                            });
                        }
                );
    }

    @Test
    public void test16() {
        Observable
                .range(1, 100)
                .map(String::valueOf)
                .subscribe(item -> System.out.println(item.getClass()));
    }

    @Test
    public void test17() {
        Observable
                .range(1, 10)
                .scan((i1, i2) -> i1 * i2)
                .subscribe(System.out::println);
    }

    @Test
    public void test18() {
        Observable
                .create(onSubscribe -> {
                    for (int i = 0; i < 10; i++) {
                        onSubscribe.onNext(i);
                        sleep(11);
                    }
                    onSubscribe.onCompleted();
                })
                .timeout(10, TimeUnit.MILLISECONDS)
                .subscribe(System.out::println);
    }

    @Test
    public void test19() {
        boolean b = doWithTest19();
        System.out.println(b);
    }

    private boolean doWithTest19() {
        TcHook<Boolean> tcHook = new TcHook<>();
        Observable.create(onSubscribe -> {
            sleep(1000);
            onSubscribe.onNext("Hello World");
            onSubscribe.onCompleted();
        }).timeout(1900, TimeUnit.MILLISECONDS)
                .toBlocking()
                .subscribe(t -> tcHook.setObject(true));
        return tcHook.getObject();
    }

    @Test
    public void test20() throws ExecutionException, InterruptedException {
        ForkJoinPool forkJoinPool = new ForkJoinPool(25);
        List<ForkJoinTask<Boolean>> tasks = Lists.newArrayList();
        for (int i = 0; i < 50; i++) {
            tasks.add(
                    forkJoinPool.submit(() -> {
                        sleep(1500);
                        return false;
                    })
            );
        }
        tasks.stream().map(task -> {
            try {
                return task.get();
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
        }).forEach(System.out::println);
    }

    @SneakyThrows
    private void sleep(long millis) {
        TimeUnit.MILLISECONDS.sleep(millis);
    }

}
