package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebFlux01 {
    @GetMapping("/webflux01/hello/{name}")
    String hello(@PathVariable String name) {
        return "WebFlux01: Hello " + name;
    }
}