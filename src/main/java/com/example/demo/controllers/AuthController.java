package com.example.demo.controllers;

import com.example.demo.config.security.LoginService;
import com.example.demo.dto.request.LoginRequestDTO;
import com.example.demo.dto.response.LoginResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Auth/Login", description = "Controller per la gestione del Login utente/account")
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Auth", description = "Endpoint per autenticazione (login) / Endpoint for authentication (login)")
public class AuthController {

    private final LoginService service;

    @Operation(summary = "Autentica l'utente", description = "Verifica le credenziali dell'utente e restituisce un token JWT in caso di successo.")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Login effettuato con successo"), @ApiResponse(responseCode = "401", description = "Credenziali non valide"), @ApiResponse(responseCode = "404", description = "Account non trovato")})
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginRequestDTO loginRequest) {
        return ResponseEntity.ok(service.login(loginRequest));
    }
}