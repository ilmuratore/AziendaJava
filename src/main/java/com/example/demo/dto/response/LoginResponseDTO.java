package com.example.demo.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.Set;

@Data
@Builder
public class LoginResponseDTO {

    @Schema(description = "ID univoco dell'account | Unique account ID", example = "1")
    private Long id;

    @Schema(description = "Username dell'account | Account username", example = "mario.rossi")
    private String username;

    @Schema(description = "Indica se l'email è verificata | Indicates if the email is verified", example = "true")
    private Boolean emailVerified;

    @Schema(description = "Indica se l'account è abilitato | Indicates if the account is enabled", example = "true")
    private Boolean enabled;

    @Schema(description = "Timestamp dell'ultimo login effettuato | Timestamp of the last login", example = "2025-05-30T12:34:56Z")
    private Instant lastLogin;

    @Schema(description = "Set di nomi dei ruoli assegnati all'account | Set of assigned role names", example = "[\"ROLE_USER\", \"ROLE_ADMIN\"]")
    private Set<String> roleNames;

    @Schema(description = "Token JWT per le richieste protette | JWT token for protected requests", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
    private String token;
}
