package com.example.gateway_service.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.List;

@Component
public class JWTUtil {

    private static final String SECRET = "vjSuperSecretJwtKeyMustBeLong123456";

    public void validateToken(final String token) {
        Jwts.parserBuilder().setSigningKey(getSigningKey());
    }

    private Key getSigningKey(){
        byte[] keyBytes= Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String extractUsername(String token) {
        System.out.println("Token "+token);
        return extractClaim(token, Claims::getSubject);

    }

    public <T> T extractClaim(String token, ClaimsResolver<T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.resolve(claims);
    }

    private Claims extractAllClaims(String token){
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    @SuppressWarnings("unchecked")
    public List<String> extractRoles(String token) {
        return extractClaim(token, claims -> (List<String>) claims.get("roles"));
    }

    @FunctionalInterface
    public interface ClaimsResolver<T> {
        T resolve(Claims claims);
    }
}
