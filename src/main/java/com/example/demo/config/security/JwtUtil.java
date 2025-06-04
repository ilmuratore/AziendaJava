package com.example.demo.config.security;

import io.jsonwebtoken.*;

import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.Set;

/**
 * Utility per operazioni JWT: generazione, estrazione dati e validazione.
 *
 * <p><strong>English:</strong> Utility for JWT operations: generation, data extraction, and validation.</p>
 * <p><strong>Italiano:</strong> Utility per operazioni JWT: generazione, estrazione dati e validazione.</p>
 *
 * <p>Usa la chiave segreta (HMAC SHA) e parametri configurati per creare e verificare token
 * JSON Web Token (JWT).</p>
 * <p><strong>English:</strong> Uses secret key (HMAC SHA) and configured parameters to create and verify
 * JSON Web Tokens (JWT).</p>
 */
@Component
public class JwtUtil {

    private final Key signingKey;
    private final Long jwtExpirationMs;

    /**
     * Costruttore che inietta la chiave segreta e la durata del token dalle proprietà.
     *
     * <p><strong>English:</strong> Constructor that injects secret key and token duration from properties.</p>
     *
     * @param secret          stringa segreta HMAC SHA per firmare il token (da application.properties)
     *                        <p><strong>English:</strong> HMAC SHA secret string to sign the token (from application.properties).</p>
     *                        <p><strong>Italiano:</strong> stringa segreta HMAC SHA per firmare il token (da application.properties).</p>
     * @param jwtExpirationMs durata del token in millisecondi (da application.properties)
     *                        <p><strong>English:</strong> token duration in milliseconds (from application.properties).</p>
     *                        <p><strong>Italiano:</strong> durata del token in millisecondi (da application.properties).</p>
     */
    public JwtUtil(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.expiration-ms}") long jwtExpirationMs
    ) {
        this.signingKey = Keys.hmacShaKeyFor(secret.getBytes());
        this.jwtExpirationMs = jwtExpirationMs;
    }

    /**
     * Genera un token JWT firmato contenente lo username e i ruoli.
     *
     * <p><strong>English:</strong> Generates a signed JWT token containing the username and roles.</p>
     *
     * <p>Campi inclusi:</p>
     * <ul>
     *     <li>{@code sub}: subject impostato come username.</li>
     *     <li>{@code roles}: lista di ruoli dell’utente.</li>
     *     <li>{@code iat}: data di emissione.</li>
     *     <li>{@code exp}: data di scadenza calcolata come <code>now + jwtExpirationMs</code>.</li>
     *     <li>Firma HMAC SHA256 con la chiave segreta {@link #signingKey}.</li>
     * </ul>
     *
     * <p><strong>English:</strong> Included claims:</p>
     * <ul>
     *     <li>{@code sub}: subject set as username.</li>
     *     <li>{@code roles}: list of user roles.</li>
     *     <li>{@code iat}: issued at timestamp.</li>
     *     <li>{@code exp}: expiration timestamp computed as <code>now + jwtExpirationMs</code>.</li>
     *     <li>HMAC SHA256 signature with secret key {@link #signingKey}.</li>
     * </ul>
     *
     * @param username stringa username da includere nel token
     *                 <p><strong>English:</strong> username string to include in token.</p>
     *                 <p><strong>Italiano:</strong> stringa username da includere nel token.</p>
     * @param roles    insieme di nomi ruoli da includere come claim
     *                 <p><strong>English:</strong> set of role names to include as a claim.</p>
     *                 <p><strong>Italiano:</strong> insieme di nomi ruoli da includere come claim.</p>
     * @return stringa token JWT compatto e firmato
     *         <p><strong>English:</strong> compact, signed JWT token string.</p>
     *         <p><strong>Italiano:</strong> stringa token JWT compatto e firmato.</p>
     */
    public String generateToken(String username, Set<String> roles){
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationMs);
        return Jwts.builder()
                .setSubject(username)
                .claim("roles" , roles)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(signingKey, SignatureAlgorithm.ES256)
                .compact();
    }

    /**
     * Estrae lo username dal token JWT valido.
     *
     * <p><strong>English:</strong> Extracts username from a valid JWT token.</p>
     *
     * @param token token JWT firmato
     *              <p><strong>English:</strong> signed JWT token.</p>
     *              <p><strong>Italiano:</strong> token JWT firmato.</p>
     * @return stringa username presente nel claim “sub”
     *         <p><strong>English:</strong> username string present in “sub” claim.</p>
     *         <p><strong>Italiano:</strong> stringa username presente nel claim “sub”.</p>
     * @throws JwtException se il parsing del token fallisce (firma non valida, malformato, scaduto)
     *                      <p><strong>English:</strong> if token parsing fails (invalid signature, malformed, expired).</p>
     *                      <p><strong>Italiano:</strong> se il parsing del token fallisce (firma non valida, malformato, scaduto).</p>
     */
    public String getUsernameFromJwt(String token){
        return Jwts.parser()
                .setSigningKey(signingKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    /**
     * Estrae il set di ruoli dal token JWT valido.
     *
     * <p><strong>English:</strong> Extracts the set of roles from a valid JWT token.</p>
     *
     * @param token token JWT firmato
     *              <p><strong>English:</strong> signed JWT token.</p>
     *              <p><strong>Italiano:</strong> token JWT firmato.</p>
     * @return insieme di nomi ruoli estratti dal claim “roles”
     *         <p><strong>English:</strong> set of role names extracted from “roles” claim.</p>
     *         <p><strong>Italiano:</strong> insieme di nomi ruoli estratti dal claim “roles”.</p>
     * @throws JwtException se il parsing del token fallisce (firma non valida, malformato, scaduto)
     *                      <p><strong>English:</strong> if token parsing fails (invalid signature, malformed, expired).</p>
     *                      <p><strong>Italiano:</strong> se il parsing del token fallisce (firma non valida, malformato, scaduto).</p>
     */
    @SuppressWarnings("unchecked")
    public Set<String> getRolesFromJwt(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(signingKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.get("roles", Set.class);
    }

    /**
     * Valida un token JWT controllando firma e scadenza.
     *
     * <p><strong>English:</strong> Validates a JWT token by checking signature and expiration.</p>
     *
     * <p>Se il token è valido, restituisce true.
     * Se scaduto, lancia {@link ExpiredJwtException}.
     * Se malformato o con firma non valida, lancia {@link MalformedJwtException}.</p>
     * <p><strong>English:</strong> If token is valid, returns true.
     * If expired, throws {@link ExpiredJwtException}.
     * If malformed or invalid signature, throws {@link MalformedJwtException}.</p>
     *
     * @param token token JWT compatto
     *              <p><strong>English:</strong> compact JWT token.</p>
     *              <p><strong>Italiano:</strong> token JWT compatto.</p>
     * @return {@code true} se il token è valido (firma verificata e non scaduto)
     *         <p><strong>English:</strong> {@code true} if token is valid (signature verified and not expired).</p>
     *         <p><strong>Italiano:</strong> {@code true} se il token è valido (firma verificata e non scaduto).</p>
     * @throws ExpiredJwtException   se il token è scaduto (data di scadenza antecedente alla data odierna)
     *                               <p><strong>English:</strong> if token is expired (expiration date before current date).</p>
     *                               <p><strong>Italiano:</strong> se il token è scaduto (data di scadenza antecedente alla data odierna).</p>
     * @throws MalformedJwtException se il token è malformato o la firma non è valida
     *                               <p><strong>English:</strong> if token is malformed or signature is invalid.</p>
     *                               <p><strong>Italiano:</strong> se il token è malformato o la firma non è valida.</p>
     * @throws JwtException          per altri errori di parsing di JWT (es. algomento non supportato)
     *                               <p><strong>English:</strong> for other JWT parsing errors (e.g., unsupported algorithm).</p>
     *                               <p><strong>Italiano:</strong> per altri errori di parsing JWT (es. algoritmo non supportato).</p>
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(signingKey)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException ex) {
            throw ex;
        } catch (JwtException | IllegalArgumentException ex) {
            throw new MalformedJwtException("Token JWT non valido", ex);
        }
    }
}
