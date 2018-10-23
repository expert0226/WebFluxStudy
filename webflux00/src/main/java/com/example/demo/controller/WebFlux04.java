package com.example.demo.controller;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.BodyInserters.fromObject;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Configuration
public class WebFlux04 {
    HandlerFunction handler = req -> {
        String res = "WebFlux04: Hello " + req.pathVariable("name");
        return ok().body(fromObject(res));
    };

    @Bean
    public RouterFunction<ServerResponse> route04() {
        return RouterFunctions.route(
                RequestPredicates.path("/webflux04/hello/{name}"),
                handler
        );
    }
}
