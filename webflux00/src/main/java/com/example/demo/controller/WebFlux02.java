package com.example.demo.controller;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Configuration
public class WebFlux02 {
    HandlerFunction helloHandler = req -> {
        String name = req.pathVariable("name");
        Mono<String> result = Mono.just("WebFlux02: Hello " + name);

        Mono<ServerResponse> res = ok().body(result, String.class);

        return res;
    };

    @Bean
    public RouterFunction<ServerResponse> route() {
        RouterFunction router = req ->
                RequestPredicates.path("/webflux02/hello/{name}").test(req) ? Mono.just(helloHandler) : Mono.empty();

        return router;
    }
}