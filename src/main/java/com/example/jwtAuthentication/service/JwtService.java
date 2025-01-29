package com.example.jwtAuthentication.service;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;
import java.util.Objects;

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

    public String getJwtToken(String username, Map<String, Object> claims) {
        return Jwts.builder()
                .claims(claims)
                .subject(username)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 20))
                .signWith(secretKey)
                .compact();
    }

    public String getUsername(String token) {
        Claims data = getTokenData(token);
        if (data == null) return null;
        return data.getSubject();
    }

     public Object getFieldFromToken(String token, String key) {
        Claims data = getTokenData(token);
        if (data == null)
            return null;
        return data.get(key);
     }

    private Claims getTokenData(String token) {
        try {
            return Jwts
                    .parser()
                    .verifyWith(secretKey).build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (Exception e) {
            return null;
        }
    }

}
