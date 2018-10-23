package com.heaven.reativeweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.time.Duration;

@SpringBootApplication
@RestController
public class Application002 {
    // 7m24s
    // 여기도 WebMVC, WebFlux 똑같이 코딩할 수 있다.
    @GetMapping(value="/stream", produces="application/stream+json")
    public Flux<User> stream() {
        return findUsers()
                .delayElements(Duration.ofSeconds(1));
    }

    private Flux<User> findUsers() {
        return Flux.just(
                new User(1, "Spring"),
                new User(2, "Web"),
                new User(3, "Flux"));
    }

    public static void main(String[] args) {
        SpringApplication.run(Application002.class, args);
    }
}
