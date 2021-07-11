package com.example.spring.webfluxv2.fluxAndMonoPlayground;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

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

    @Test
    public void fluxElementsWithoutErrorTest() {
        Flux<String> flux = Flux
                .just("one", "two", "three")
                .log();
        //if change the order then test fails
        StepVerifier.create(flux)
                .expectNext("one", "two", "three")
                .verifyComplete();
    }

    @Test
    public void fluxElementsWithErrorTest() {
        Flux<String> flux = Flux
                .just("one", "two", "three")
                .concatWith(Flux.error(new RuntimeException()))
                .log();
        StepVerifier.create(flux)
                .expectNext("one")
                .expectNext("two")
                .expectNext("three")
                .expectError(RuntimeException.class)
                .verify();
    }

    @Test
    public void fluxElementsCountTest() {
        Flux<String> flux = Flux
                .just("one", "two", "three")
                .log();
        StepVerifier.create(flux)
                .expectNextCount(3)
                .verifyComplete();
    }

    @Test
    public void monoTest() {
        Mono<String> mono = Mono.just("Mono")
                .log();
        StepVerifier.create(mono)
                .expectNext("Mono")
                .verifyComplete();
    }

    @Test
    public void monoTestWithError() {
        StepVerifier.create(Mono.error(new RuntimeException()).log())
                .expectError(RuntimeException.class)
                .verify();
    }

}
