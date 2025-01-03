package com.example.jwtAuthentication.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class HomeController {

    @GetMapping
    public String getHello() {
        return "hello world";
    }

    @PostMapping("/login")
    public String login() {
        return "User login successfully";
    }
}
