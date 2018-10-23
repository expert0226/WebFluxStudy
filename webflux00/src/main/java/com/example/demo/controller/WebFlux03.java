package com.example.demo.controller;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.BodyInserters.fromObject;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Configuration
public class WebFlux03 {
    @Bean
    public RouterFunction<ServerResponse> route03() {
        return RouterFunctions.route(
                RequestPredicates.path("/webflux03/hello/{name}"),
                req -> ok().body(fromObject("WebFlux03: Hello " + req.pathVariable("name")))
        );
    }
}