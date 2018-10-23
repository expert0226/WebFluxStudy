package com.heaven.reativeweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@SpringBootApplication
@RestController
public class Application005 {
// cf: https://www.baeldung.com/spring-5-webclient
//        private WebClient client = WebClient.builder()
//            .baseUrl("http://localhost:8080")
//            .build();
    private WebClient client = WebClient.create("http://localhost:8080");

    @GetMapping("/api/hello")
    public Mono<String> hello() {
        return Mono.just("hello");
    }

    @GetMapping("/api")
    public Mono<String> helloApi() {
        return client.get()
                .uri("/api/hello")
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, res -> Mono.error(new IllegalArgumentException()))
                .onStatus(HttpStatus::is5xxServerError, res -> Mono.error(new RuntimeException()))
                .bodyToMono(String.class)
                .map(body -> body.toUpperCase())
                .switchIfEmpty(Mono.just("Empty"));
    }

    @GetMapping(value = "/api2", produces = "text/event-stream")
    public Flux<String> helloStream() {
        return client.get()
                // Application002.java
                .uri("/stream")
                .accept(MediaType.APPLICATION_STREAM_JSON)
                .exchange()
                // @NoArgsConstructor 필요
                .flatMapMany(res -> res.bodyToFlux(User.class))
                .filter(user -> user.getId() > 1)
                .map(user -> user.toString());
    }

    public static void main(String[] args) {
        SpringApplication.run(Application005.class, args);
    }
}
