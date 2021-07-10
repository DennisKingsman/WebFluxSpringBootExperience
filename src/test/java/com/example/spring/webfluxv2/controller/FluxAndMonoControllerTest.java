package com.example.spring.webfluxv2.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@WebFluxTest
public class FluxAndMonoControllerTest {

    //also used for restTemplate
    @Autowired
    WebTestClient webTestClient;

    //fails if Delay more than 1 sec
    @Test
    public void fluxApproachOne() {
        Flux<Integer> streamFlux = webTestClient.get().uri("/fluxAndMono/flux")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .returnResult(Integer.class)
                .getResponseBody();

        StepVerifier.create(streamFlux)
                .expectNext(1, 2, 3, 4)
                .verifyComplete();
    }

    @Test
    public void fluxApproachTwo() {
        webTestClient.get().uri("/fluxAndMono/flux")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBodyList(Integer.class)
                .hasSize(4);
    }

    @Test
    public void fluxApproachThree() {
        List<Integer> expected = List.of(1, 2, 3, 4);
        EntityExchangeResult<List<Integer>> entityExchangeResult =
                webTestClient.get().uri("/fluxAndMono/flux")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Integer.class)
                .returnResult();
        assertEquals(expected, entityExchangeResult.getResponseBody());
    }

    @Test
    public void fluxApproachFour() {
        List<Integer> expectedIntegerList = Arrays.asList(1, 2, 3, 4);
        webTestClient
                .get().uri("/fluxAndMono/flux")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Integer.class)
                .consumeWith((response) -> {
                    assertEquals(expectedIntegerList, response.getResponseBody());
                });
    }

}
