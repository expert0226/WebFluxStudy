package com.heaven.reativeweb;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@SpringBootApplication
@RestController
@Slf4j
public class Application003 {
    // WebMVC 에서는 불가
    // curl -X POST http://localhost:8080/update -H "Content-Type: application/json" -d "{ 'id': 6, 'name': 'John' }"
    // 응답은 없음
    // PostMan: static/images/Application003/001.PNG
    @PostMapping("/update")
    public Mono<Void> hello(@RequestBody Mono<User> user) {
        return save(user).then();
    }

    private Mono<Void> save(Mono<User> user) {
        // 응답이 없기 때문에 로그에라도 출력을....
        log.info("I am in save.");
        return Mono.empty();
    }

    public static void main(String[] args) {
        SpringApplication.run(Application003.class, args);
    }
}
