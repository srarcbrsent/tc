package com.ysu.zyw.tc.components.reactive;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

@Slf4j
public class TcReactiveDemos {

    @Test
    public void test01() {
        Flux.fromIterable(Lists.newArrayList(1L, 2L, 3L, 4L))
                .mergeWith(Flux.interval(Duration.of(1000, ChronoUnit.MILLIS)))
                .map(d -> d * 2)
                .take(3)
                .subscribe(System.out::println);
    }

}
