package com.example.jwtAuthentication.service;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.util.Date;

@Service
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

    public String getJwtToken() {

        return Jwts.builder()
                .subject("Ravindu")
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000*60*20))
                .signWith(secretKey)
                .compact();
    }

    public String getUsername(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build().
                parseSignedClaims(token).
                getPayload().
                getSubject();
    }
}
