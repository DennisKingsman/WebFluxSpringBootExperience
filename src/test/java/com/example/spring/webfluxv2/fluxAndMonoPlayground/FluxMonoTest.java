package com.example.spring.webfluxv2.fluxAndMonoPlayground;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class FluxMonoTest {

    @Test
    public void fluxTest() {
        Flux.just("1", "2", "3")
                .concatWith(Flux.just("str"))
//                .concatWith(Flux.error(new RuntimeException("Test error")))
                .log()
                .concatWith(Mono.just(" mono"))
                .subscribe(
                        System.out::println,
                        (e) -> System.err.println(e.getMessage()),
                        () -> System.out.println("Complete"));
    }

}
