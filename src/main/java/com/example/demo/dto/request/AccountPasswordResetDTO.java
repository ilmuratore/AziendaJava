package com.example.demo.dto.request;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;


/**
 * DTO per il reset della password di un account.
 * DTO for resetting an account password.
 */
@Getter
@Setter
public class AccountPasswordResetDTO {
     /**
     * Username dell’account di cui resettare la password.
     * Username of the account for which to reset the password.
     */
      @NotBlank(message = "username è obbligatorio / username is required")
    private String username;

 /**
     * Nuova password da assegnare all’account.
     * - Deve contenere almeno 8 caratteri e massimo 100.
     * - Deve includere almeno una lettera minuscola, una maiuscola, un numero e un carattere speciale.
     *
     * New password to assign to the account.
     * - Must be between 8 and 100 characters.
     * - Must include at least one lowercase letter, one uppercase letter, one digit, and one special character.
     */
    @NotBlank(message = "La nuova password è obbligatoria / new password is required")
   @Size(
        min = 8, max = 100,
        message = "La nuova password deve avere tra 8 e 100 caratteri / new password must be between 8 and 100 characters"
    )
    @Pattern(
        regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&].*$",
        message = "La nuova password deve contenere almeno una lettera minuscola, maiuscola, numero e carattere speciale / new password must contain at least one lowercase letter, one uppercase letter, one digit, and one special character"
    )
    private String newPassword;
}
