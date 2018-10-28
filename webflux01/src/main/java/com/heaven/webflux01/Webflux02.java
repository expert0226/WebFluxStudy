package com.heaven.webflux01;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.Map;

import static org.springframework.web.reactive.function.BodyExtractors.toFormData;
import static org.springframework.web.reactive.function.BodyExtractors.toMono;
import static org.springframework.web.reactive.function.BodyInserters.fromObject;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Slf4j
@Component
public class Webflux02 {
    @Bean
    RouterFunction<ServerResponse> route001() {
        return RouterFunctions.route(
                GET("/webflux02/hello"),
                req -> ok().body(fromObject("Hello WebFlux"))
        );
    }

    @Bean
    RouterFunction<ServerResponse> route002() {
        return RouterFunctions.route(
                GET("/webflux02/hello/{name}"),
                req -> ok().body(fromObject("Hello " + req.pathVariable("name")))
        );
    }

    @Bean
    RouterFunction<ServerResponse> route003() {
        return RouterFunctions.route(
                GET("/webflux02/query"),
                req -> ok()
                        .body(
                                fromObject(
                                        String.format(
                                                "name: %s, age: %s",
                                                req.queryParam("name"),
                                                req.queryParam("age")
                                        )
                                )
                        )
        );
    }

    @Bean
    RouterFunction<ServerResponse> route004() {
        return RouterFunctions.route(POST("/webflux02/post"),
                req -> req.body(toMono(User.class))
                        .doOnNext(user -> log.info(user.toString()))
                        .then(ok().build()));


//.route(
//            POST("/webflux02/post"),
//            req -> req.body(toMono(User.class))
//                    .doOnNext(user -> System.out.printf("name: " + user.getName() + ", age: " + user.getAge()))
//                    .then(ok().build())
//        );
    }
}

@Data
class User {
    private String name;
    private Integer age;
}