package com.example.demo.config.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.*;

import javax.crypto.SecretKey;


@Component
public class JwtUtil {

    private final Key signingKey;
    private final Long jwtExpirationMs;


    public JwtUtil(@Value("${jwt.secret}") String base64Secret,
                   @Value("${jwt.expiration-ms}") long jwtExpirationMs) {
        this.signingKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(base64Secret));
        this.jwtExpirationMs = jwtExpirationMs;
    }


    public String generateToken(String username, Set<String> roles) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationMs);
        return Jwts.builder()
                .subject(username)
                .claim("roles", roles)
                .issuedAt(now)
                .expiration(expiryDate)
                .signWith(signingKey)
                .compact();
    }

    
    public String getUsernameFromJwt(String token) {
        return Jwts.parser()
                .verifyWith((SecretKey) signingKey)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    @SuppressWarnings("unchecked")
    public Set<String> getRolesFromJwt(String token) {
        Claims claims = Jwts.parser()
                .verifyWith((SecretKey) signingKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
        
        List<String> rolesList = claims.get("roles", List.class);
        if (rolesList == null) {
            return Collections.emptySet();
        }
        return new HashSet<>(rolesList);
    }


    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith((SecretKey) signingKey)
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (ExpiredJwtException ex) {
            throw ex;
        } catch (JwtException | IllegalArgumentException ex) {
            throw new MalformedJwtException("Token JWT non valido", ex);
        }
    }
}