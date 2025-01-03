package com.example.jwtAuthentication.service;


import io.jsonwebtoken.security.Keys;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class JwtService {

    private SecretKey secretKey;

    public JwtService() {
        try {
            SecretKey sKey = KeyGenerator.getInstance("HmacSHA256").generateKey();
            secretKey = Keys.hmacShaKeyFor(sKey.getEncoded());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
