package com.example.demo.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Schema(name = "AccountPasswordReset", description = "Payload per resettare password account | Payload to reset account password")
public class AccountPasswordResetDTO {

    @NotBlank(message = "username è obbligatorio / username is required")
    private String username;

    @NotBlank(message = "La nuova password è obbligatoria / new password is required")
    @Size(min = 8, max = 100, message = "La nuova password deve avere tra 8 e 100 caratteri / new password must be between 8 and 100 characters")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&].*$", message = "La nuova password deve contenere almeno una lettera minuscola, maiuscola, numero e carattere speciale / new password must contain at least one lowercase letter, one uppercase letter, one digit, and one special character")
    private String newPassword;
}
