package com.example.jwtAuthentication.controller;

import com.example.jwtAuthentication.service.JwtService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
//@AllArgsConstructor
//@RequiredArgsConstructor
public class HomeController {

    private final JwtService jwtService;

    public HomeController(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @GetMapping
    public String getHello() {
        return "hello world";
    }

//    @PostMapping("/login")
//    public String login() {
//        return "User login successfully";
//    }

    @PostMapping("/login")
    public String login() {
        return jwtService.getJwtToken();
    }

    @GetMapping("/username")
    public String getUsername(@RequestParam String token) {
        return jwtService.getUsername(token);
    }
}
