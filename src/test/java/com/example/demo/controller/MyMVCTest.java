package com.example.demo.controller;

// 참고: https://grokonez.com/testing/springboot-webflux-test-webfluxtest

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

@RunWith(SpringRunner.class)
@WebFluxTest
public class MyMVCTest {
    @Autowired
    private WebTestClient webClient;

    @Test
    public void helloTest() throws Exception {
        webClient.get()
                .uri("/mvc/hello/{name}", "spring")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .isEqualTo("MVC: Hello spring");
    }
}