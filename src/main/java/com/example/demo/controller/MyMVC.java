package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyMVC {
    @GetMapping("/mvc/hello/{name}")
    String hello(@PathVariable String name) {
        return "MVC: Hello " + name;
    }
}