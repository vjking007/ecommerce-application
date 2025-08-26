package com.example.user_service.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class JwtService {

    @Value("${jwt.expiration}")
    private long jwtExpirationInMs;

    // Secret must be at least 256-bit (i.e. 32+ characters)
    private final String SECRET = "vjSuperSecretJwtKeyMustBeLong123456";

    private final Key key = Keys.hmacShaKeyFor(SECRET.getBytes());

    public String generateToken(String username, Long id) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", id); // ✅ userId in token

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 600000)) // 1 day
                .signWith(key, SignatureAlgorithm.HS256) // correct method
                .compact();
    }

    public String extractUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key) //use Key
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return username.equals(userDetails.getUsername());
    }

    public boolean isTokenValid(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}