package com.example.demo.config.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.*;


@Component
public class JwtUtil {

    private final Key signingKey;
    private final Long jwtExpirationMs;


    public JwtUtil(@Value("${jwt.secret}") String secret, @Value("${jwt.expiration-ms}") long jwtExpirationMs) {
        this.signingKey = Keys.hmacShaKeyFor(secret.getBytes());
        this.jwtExpirationMs = jwtExpirationMs;
    }


    public String generateToken(String username, Set<String> roles) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationMs);
        return Jwts.builder().setSubject(username).claim("roles", roles).setIssuedAt(now).setExpiration(expiryDate).signWith(signingKey, SignatureAlgorithm.HS256) // ✅ CAMBIATO da ES256 a HS256
                .compact();
    }


    public String getUsernameFromJwt(String token) {
        return Jwts.parser().setSigningKey(signingKey).build().parseClaimsJws(token).getBody().getSubject();
    }

    @SuppressWarnings("unchecked")
    public Set<String> getRolesFromJwt(String token) {
        Claims claims = Jwts.parser().setSigningKey(signingKey).build().parseClaimsJws(token).getBody();
        List<String> rolesList = claims.get("roles", List.class);
        if (rolesList == null) {
            return Collections.emptySet();
        }
        return new HashSet<>(rolesList);
    }


    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(signingKey).build().parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException ex) {
            throw ex;
        } catch (JwtException | IllegalArgumentException ex) {
            throw new MalformedJwtException("Token JWT non valido", ex);
        }
    }
}