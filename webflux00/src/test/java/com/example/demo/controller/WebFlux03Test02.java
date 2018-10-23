package com.example.demo.controller;

// 참고: https://github.com/spring-projects/spring-framework/blob/master/spring-test/src/test/java/org/springframework/test/web/reactive/server/samples/ResponseEntityTests.java

// Add build.gradle
// compile group: 'javax.el', name: 'javax.el-api', version: '3.0.0'
// compile group: 'org.glassfish', name: 'javax.el', version: '3.0.0'

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

public class WebFlux03Test02 {
    private final WebTestClient client = WebTestClient.bindToController(new WebFlux03().route03())
            .configureClient()
            .baseUrl("/webflux03/hello/")
            .build();

    @Test
    public void helloTest() {
        client.get()
                .uri("fall")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .isEqualTo("WebFlux03: Hello fall");
    }
}
