package com.example.spring.webfluxv2.fluxAndMonoPlayground;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;

public class FluxMonoTest {

    @Test
    public void fluxTest() {
        Flux.just("1", "2", "3").subscribe(System.out::println);
    }

}
