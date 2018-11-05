package com.heaven.webflux02;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Slf4j
@Component
public class RouteFunction01 {
    @Bean
    RouterFunction<ServerResponse> route001() {
        HandlerFunction helloHandlerFunction = req -> {
            Mono<String> result = Mono.just("Hello handlerFunction");
            Mono<ServerResponse> res = ok().body(result, String.class);
            return res;
        };

        return RouterFunctions.route(GET("/hello"), helloHandlerFunction);
    }
}