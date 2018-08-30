package com.example.demo.controller;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.BodyInserters.fromObject;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Configuration
public class WebFlux05 {
    HandlerFunction handler = req -> {
        String res = "WebFlux05: Hello " + req.pathVariable("name");
        return ok().body(fromObject(res));
    };

    @Bean
    public RouterFunction<ServerResponse> route05() {
        return RouterFunctions.route(
                GET("/webflux05/hello/{name}"),
                handler
        );
    }
}
