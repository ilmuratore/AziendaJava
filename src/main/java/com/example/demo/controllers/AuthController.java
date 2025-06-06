package com.example.demo.controllers;

import com.example.demo.dto.request.LoginRequestDTO;
import com.example.demo.dto.response.LoginResponseDTO;
import com.example.demo.config.security.LoginService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller per gestire le richieste di autenticazione (login).
 *
 * <p><strong>English:</strong> Controller to handle authentication (login) requests.</p>
 */
@RestController
@RequestMapping("/api/auth") // Rimosso "api/auth" duplicato
@Tag(
        name = "Auth",
        description = "Endpoint per autenticazione (login) / Endpoint for authentication (login)"
)
public class AuthController {

    private final LoginService service;

    /**
     * Costruisce un'istanza di {@code AuthController} con il servizio di login.
     *
     * <p><strong>English:</strong> Constructs an {@code AuthController} with the login service.</p>
     *
     * @param service il servizio di login
     *                <p><strong>English:</strong> the login service.</p>
     */
    public AuthController(LoginService service) {
        this.service = service;
    }

    /**
     * Effettua il login utilizzando username e password.
     *
     * <p><strong>English:</strong> Performs login using username and password.</p>
     *
     * @param loginRequest DTO contenente username e password
     *                <p><strong>English:</strong> DTO containing username and password.</p>
     * @return {@code ResponseEntity} con {@link LoginResponseDTO} e stato HTTP 200 (OK)
     *         <p><strong>English:</strong> {@code ResponseEntity} with {@link LoginResponseDTO} and HTTP status 200 (OK).</p>
     */
    @Operation(
            summary = "Autentica l'utente",
            description = "Verifica le credenziali dell'utente e restituisce un token JWT in caso di successo."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login effettuato con successo"),
            @ApiResponse(responseCode = "401", description = "Credenziali non valide"),
            @ApiResponse(responseCode = "404", description = "Account non trovato")
    })
    @PostMapping("/login") // Cambiato da "/auth/login" a "/login"
    public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginRequestDTO loginRequest) {
        return ResponseEntity.ok(service.login(loginRequest));
    }
}