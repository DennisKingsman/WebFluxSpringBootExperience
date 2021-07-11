package com.example.spring.webfluxv2.handler;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureWebTestClient
public class SimpleHandlerFunctionTest {

    @Autowired
    WebTestClient webTestClient;

    @Test
    public void fluxApproachOneTest() {
        Flux<Integer> streamFlux = webTestClient.get().uri("/fun/flux")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .returnResult(Integer.class)
                .getResponseBody();

        StepVerifier.create(streamFlux)
                .expectNext(1, 2, 3)
                .verifyComplete();
    }

    @Test
    public void monoTest() {
        int expected = 1;
        webTestClient.get().uri("/fun/mono")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Integer.class)
                .consumeWith((response) -> {
                    assertEquals(expected, response.getResponseBody());
                });
    }

}
