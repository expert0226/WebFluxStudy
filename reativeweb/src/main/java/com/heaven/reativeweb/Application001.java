package com.heaven.reativeweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class Application001 {
    // 6m35s
    // WebFlux 코드를 WebMVC 와 같게 만들 수 있다.
    // spring-boot-starter-web: WebMVC - Servlet 기반
    // spring-boot-starter-webflux: WebFlux - Non Servlet
    @GetMapping("/hello/{name}")
    public String hello(@PathVariable String name) {
        return "Hello " + name;
    }

    public static void main(String[] args) {
        SpringApplication.run(Application001.class, args);
    }
}
