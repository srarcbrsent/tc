package com.ysu.zyw.tc.components.reactive;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import reactor.core.publisher.Flux;

@Slf4j
public class TcReactiveDemos {

    @Test
    public void test01() {
        Flux.fromIterable(Lists.newArrayList(1L, 2L, 3L, 4L))
                .parallel(5)
                .map(Math::sqrt)
                .log()
                .doOnNext(ele -> System.out.println(Thread.currentThread()))
                .subscribe(System.out::println);
    }

}
