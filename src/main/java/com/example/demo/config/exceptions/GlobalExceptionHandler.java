package com.example.demo.config.exceptions;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * GlobalExceptionHandler gestisce in modo centralizzato tutte le eccezioni lanciate dai controller.
 *
 * <p><strong>English:</strong> Centralized exception handler for the entire application.
 * Catches specific exceptions and returns appropriate HTTP responses.</p>
 * <p><strong>Italiano:</strong> Gestore centralizzato delle eccezioni per tutta l’applicazione.
 * Intercetta eccezioni specifiche e restituisce risposte HTTP adeguate.</p>
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Gestisce le eccezioni di validazione dei parametri (ad es. annotazioni @Valid).
     *
     * <p><strong>English:</strong> Handles MethodArgumentNotValidException, which is thrown
     * when @Valid annotated request bodies or parameters fail validation.
     * Returns a map where the key is the field name and the value is the error message.</p>
     * <p><strong>Italiano:</strong> Gestisce MethodArgumentNotValidException, lanciata
     * quando la validazione di un corpo o parametro annotato con @Valid fallisce.
     * Restituisce una mappa con chiave il nome del campo e valore il messaggio di errore.</p>
     *
     * @param ex l’eccezione di tipo MethodArgumentNotValidException
     *           <p><strong>English:</strong> the exception instance thrown when validation fails.</p>
     *           <p><strong>Italiano:</strong> l’istanza dell’eccezione lanciata quando la validazione fallisce.</p>
     * @return ResponseEntity contenente una mappa di campi e relativi messaggi di errore con status 400 (Bad Request)
     *         <p><strong>English:</strong> a ResponseEntity with a map of field names and error messages, HTTP status 400.</p>
     *         <p><strong>Italiano:</strong> una ResponseEntity con una mappa di nomi di campo e messaggi di errore, stato HTTP 400.</p>
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String field = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            errors.put(field, message);
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    /**
     * Gestisce le eccezioni di tipo EntityNotFoundException.
     *
     * <p><strong>English:</strong> Handles EntityNotFoundException, returning 404 Not Found
     * and the exception’s message in the body.</p>
     * <p><strong>Italiano:</strong> Gestisce EntityNotFoundException,
     * restituendo 404 Not Found e il messaggio dell’eccezione nel body.</p>
     *
     * @param ex l’eccezione di tipo EntityNotFoundException
     *           <p><strong>English:</strong> the exception instance indicating that an entity was not found.</p>
     *           <p><strong>Italiano:</strong> l’istanza dell’eccezione che indica che un’entità non è stata trovata.</p>
     * @return ResponseEntity con il messaggio dell’eccezione e status 404 (Not Found)
     *         <p><strong>English:</strong> a ResponseEntity containing the exception message and HTTP status 404.</p>
     *         <p><strong>Italiano:</strong> una ResponseEntity contenente il messaggio dell’eccezione e stato HTTP 404.</p>
     */
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> handleNotFound(EntityNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    /**
     * Gestisce le eccezioni di credenziali non valide (BadCredentialsException).
     *
     * <p><strong>English:</strong> Handles BadCredentialsException, returning 401 Unauthorized
     * when login credentials are incorrect.</p>
     * <p><strong>Italiano:</strong> Gestisce BadCredentialsException,
     * restituendo 401 Unauthorized quando le credenziali di accesso sono errate.</p>
     *
     * @param ex l’eccezione di tipo BadCredentialsException
     *           <p><strong>English:</strong> the exception instance for invalid credentials.</p>
     *           <p><strong>Italiano:</strong> l’istanza dell’eccezione per credenziali non valide.</p>
     * @return ResponseEntity con il messaggio dell’eccezione e status 401 (Unauthorized)
     *         <p><strong>English:</strong> a ResponseEntity with the exception message and HTTP status 401.</p>
     *         <p><strong>Italiano:</strong> una ResponseEntity con il messaggio dell’eccezione e stato HTTP 401.</p>
     */
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<String> handleBadCredentials(BadCredentialsException ex) {
        // Nel caso di credenziali sbagliate, restituisce 401 Unauthorized
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
    }

    /**
     * Gestisce le eccezioni di token JWT scaduto.
     *
     * <p><strong>English:</strong> Handles ExpiredJwtException, returning 401 Unauthorized
     * and a fixed message indicating that the token has expired and login is required again.</p>
     * <p><strong>Italiano:</strong> Gestisce ExpiredJwtException,
     * restituendo 401 Unauthorized e un messaggio fisso che indica che il token è scaduto e
     * bisogna effettuare nuovamente il login.</p>
     *
     * @param ex l’eccezione di tipo ExpiredJwtException
     *           <p><strong>English:</strong> the exception instance for an expired JWT token.</p>
     *           <p><strong>Italiano:</strong> l’istanza dell’eccezione per un token JWT scaduto.</p>
     * @return ResponseEntity con un messaggio fisso e status 401 (Unauthorized)
     *         <p><strong>English:</strong> a ResponseEntity with a fixed message and HTTP status 401.</p>
     *         <p><strong>Italiano:</strong> una ResponseEntity con un messaggio fisso e stato HTTP 401.</p>
     */
    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<String> handleExpiredJwt(ExpiredJwtException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body("Token scaduto: devi effettuare nuovamente il login");
    }


    /**
     * Gestisce le eccezioni di token JWT malformato o non supportato.
     *
     * <p><strong>English:</strong> Handles MalformedJwtException and UnsupportedJwtException,
     * returning 401 Unauthorized and including the exception’s message in the response.</p>
     * <p><strong>Italiano:</strong> Gestisce MalformedJwtException e UnsupportedJwtException,
     * restituendo 401 Unauthorized e includendo il messaggio dell’eccezione nella risposta.</p>
     *
     * @param ex l’eccezione di tipo MalformedJwtException o UnsupportedJwtException
     *           <p><strong>English:</strong> the exception instance indicating an invalid JWT token.</p>
     *           <p><strong>Italiano:</strong> l’istanza dell’eccezione che indica un token JWT non valido.</p>
     * @return ResponseEntity con il messaggio dell’eccezione e status 401 (Unauthorized)
     *         <p><strong>English:</strong> a ResponseEntity with the exception’s message and HTTP status 401.</p>
     *         <p><strong>Italiano:</strong> una ResponseEntity con il messaggio dell’eccezione e stato HTTP 401.</p>
     */
    @ExceptionHandler({MalformedJwtException.class, UnsupportedJwtException.class})
    public ResponseEntity<String> handleInvalidJwt(Exception ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body("Token JWT non valido: " + ex.getMessage());
    }

    /**
     * Gestisce le eccezioni di argomento illegale (IllegalArgumentException).
     *
     * <p><strong>English:</strong> Handles IllegalArgumentException, returning 400 Bad Request
     * and the exception’s message.</p>
     * <p><strong>Italiano:</strong> Gestisce IllegalArgumentException,
     * restituendo 400 Bad Request e il messaggio dell’eccezione.</p>
     *
     * @param ex l’eccezione di tipo IllegalArgumentException
     *           <p><strong>English:</strong> the exception instance for an illegal argument.</p>
     *           <p><strong>Italiano:</strong> l’istanza dell’eccezione per un argomento non valido.</p>
     * @return ResponseEntity con il messaggio dell’eccezione e status 400 (Bad Request)
     *         <p><strong>English:</strong> a ResponseEntity with the exception’s message and HTTP status 400.</p>
     *         <p><strong>Italiano:</strong> una ResponseEntity con il messaggio dell’eccezione e stato HTTP 400.</p>
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgument(IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    /**
     * Gestisce tutte le altre eccezioni non catturate specificamente.
     *
     * <p><strong>English:</strong> Generic fallback handler for any Exception not handled by
     * the more specific methods. Returns 500 Internal Server Error with the exception’s message.</p>
     * <p><strong>Italiano:</strong> Gestore generico di fallback per qualsiasi Exception non
     * catturata dai metodi specifici. Restituisce 500 Internal Server Error con il messaggio dell’eccezione.</p>
     *
     * @param ex l’eccezione di tipo Exception
     *           <p><strong>English:</strong> the exception instance for an unspecified error.</p>
     *           <p><strong>Italiano:</strong> l’istanza dell’eccezione per un errore non specificato.</p>
     * @return ResponseEntity con il messaggio dell’eccezione e status 500 (Internal Server Error)
     *         <p><strong>English:</strong> a ResponseEntity with the exception’s message and HTTP status 500.</p>
     *         <p><strong>Italiano:</strong> una ResponseEntity con il messaggio dell’eccezione e stato HTTP 500.</p>
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleAll(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Errore interno: " + ex.getMessage());
    }
}
