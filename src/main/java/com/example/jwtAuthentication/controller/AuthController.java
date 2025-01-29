package com.example.jwtAuthentication.controller;

import com.example.jwtAuthentication.dto.LoginRequestDto;
import com.example.jwtAuthentication.dto.LoginResponseDto;
import com.example.jwtAuthentication.entity.AppUser;
import com.example.jwtAuthentication.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public List<AppUser> getAllUsers() {
        return authService.getAllUsers();
    }

    @PostMapping
    public AppUser createUser(@RequestBody AppUser appUser) {
        return authService.createUser(appUser);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto loginData) {
        LoginResponseDto loginResponse = authService.login(loginData);
        if (loginResponse.getError() != null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(loginResponse);

        return ResponseEntity.status(HttpStatus.OK).body(loginResponse);
    }
}
