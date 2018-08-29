package com.example.demo.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

@RunWith(SpringRunner.class)
@WebFluxTest(WebFlux03.class)
public class WebFlux03Test {
    @Autowired
    private WebTestClient webClient;

    @Test
    public void helloTest() throws Exception {
        webClient.get()
                .uri("/webflux03/hello/{name}", "fall")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .isEqualTo("WebFlux03: Hello fall");
    }
}