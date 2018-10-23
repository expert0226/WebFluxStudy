package com.heaven.webflux01;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@SpringBootApplication
@RestController
public class Webflux01Application {
	@GetMapping("/hello")
	public String hello() {
		return "Hello WebFlux!!!";
	}

	@GetMapping("/hello/mono")
	public Mono<String> helloMono() {
		return Mono.just("Hello Mono!!!");
	}

    @GetMapping("/hello/flux")
    public Flux<String> helloFlux() {
        return Flux.just("Hello Flux!!!", "Hello Reactor 3!!!", "Hello Reactive Streams!!!");
    }

	public static void main(String[] args) {
		SpringApplication.run(Webflux01Application.class, args);
	}
}