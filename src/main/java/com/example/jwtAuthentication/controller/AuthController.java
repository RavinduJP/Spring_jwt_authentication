package com.example.jwtAuthentication.controller;

import com.example.jwtAuthentication.entity.User;
import com.example.jwtAuthentication.service.AuthService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping
    public List<User> getAllUsers() {
        return authService.getAllUsers();
    }

    @PostMapping
    public User createUser(@RequestBody User user) {
        return authService.createUser(user);
    }
}
