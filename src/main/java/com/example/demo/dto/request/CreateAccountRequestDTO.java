package com.example.demo.dto.request;

import com.example.demo.config.validation.ExistingPersona;
import com.example.demo.config.validation.ExistingRoles;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;


/**
 * DTO per la creazione di un nuovo account.
 * DTO for creating a new account.
 */
@Setter
@Getter
public class CreateAccountRequestDTO {

/**
     * Username desiderato per il nuovo account.
     * - Deve essere obbligatorio.
     * - Tra 3 e 50 caratteri.
     * - Può contenere solo lettere, numeri, punti, underscore e trattini.
     *
     * Desired username for the new account.
     * - Must be provided.
     * - Length between 3 and 50 characters.
     * - Only letters, digits, dots, underscores, and hyphens are allowed.
     */
   @NotBlank(message = "username non può essere vuoto / username cannot be blank")
    @Size(
        min = 3, max = 50,
        message = "username deve avere tra 3 e 50 caratteri / username must be between 3 and 50 characters"
    )
    @Pattern(
        regexp = "^[a-zA-Z0-9._-]+$",
        message = "username può contenere solo lettere, numeri, punti, underscore e trattini / username can contain only letters, numbers, dots, underscores, and hyphens"
    )
    private String username;
 /**
     * Password per il nuovo account.
     * - Deve essere obbligatoria.
     * - Tra 8 e 100 caratteri.
     * - Deve contenere almeno una lettera minuscola, una maiuscola, un numero e un carattere speciale.
     *
     * Password for the new account.
     * - Must be provided.
     * - Length between 8 and 100 characters.
     * - Must include at least one lowercase letter, one uppercase letter, one digit, and one special character.
     */
    @NotBlank(message = "password non può essere vuota / password cannot be blank")
    @Size(
        min = 8, max = 100,
        message = "password deve avere tra 8 e 100 caratteri / password must be between 8 and 100 characters"
    )
    @Pattern(
        regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&].*$",
        message = "password deve contenere almeno una lettera minuscola, maiuscola, numero e carattere speciale / password must contain at least one lowercase letter, one uppercase letter, one digit, and one special character"
    )
    private String password;

   /**
     * ID della Persona a cui associare l’account.
     * - Deve esistere nel sistema.
     *
     * ID of the Persona to associate with the account.
     * - Must exist in the system.
     */
    @NotNull(message = "personaId è obbligatorio / personaId is required")
    @ExistingPersona(message = "personaId non corrisponde a nessuna Persona esistente / personaId does not correspond to any existing Persona")
    private Long personaId;

    /**
     * Insieme di ID di Role da assegnare all’account.
     * - Deve contenere almeno un ID.
     * - Ogni ID deve corrispondere a un ruolo esistente.
     *
     * Set of Role IDs to assign to the account.
     * - Must contain at least one ID.
     * - Each ID must correspond to an existing role.
     */
    @NotEmpty(message = "roleIds non può essere vuoto / roleIds cannot be empty")
    @ExistingRoles(message = "Almeno uno dei roleIds non è valido / At least one of the roleIds is invalid")
    private Set<Long> roleIds;
}
