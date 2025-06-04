package com.example.demo.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * DTO per la richiesta di login: username + password.
 *
 * <p><strong>English:</strong> DTO for login request: username + password.</p>
 */
@Data
public class LoginRequestDTO {

    @Schema(description = "Username dell'account | Account username", example = "mario.rossi", required = true)
    @NotBlank(message = "Username non può essere vuoto | Username cannot be blank")
    private String username;

    @Schema(description = "Password in chiaro | Plain text password", example = "P@ssw0rd!", required = true)
    @NotBlank(message = "Password non può essere vuota | Password cannot be blank")
    private String password;
}
