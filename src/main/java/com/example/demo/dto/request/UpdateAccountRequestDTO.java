package com.example.demo.dto.request;

import com.example.demo.config.validation.ExistingPersona;
import com.example.demo.config.validation.ExistingRoles;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

/**
 * DTO per l’aggiornamento di un account esistente.
 * DTO for updating an existing account.
 */
@Getter
@Setter
public class UpdateAccountRequestDTO {

    /**
     * Nuovo username da assegnare all’account (opzionale).
     * - Se non fornito, il campo non viene modificato.
     * - Tra 3 e 50 caratteri.
     * - Può contenere solo lettere, numeri, punti, underscore e trattini.
     *
     * New username to assign to the account (optional).
     * - If not provided, the field is not changed.
     * - Length between 3 and 50 characters.
     * - Only letters, digits, dots, underscores, and hyphens are allowed.
     */
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
     * Nuova password da assegnare all’account (opzionale).
     * - Se non fornito, la password non viene modificata.
     * - Tra 8 e 100 caratteri.
     * - Deve contenere almeno una lettera minuscola, una maiuscola, un numero e un carattere speciale.
     *
     * New password to assign to the account (optional).
     * - If not provided, the password is not changed.
     * - Length between 8 and 100 characters.
     * - Must include at least one lowercase letter, one uppercase letter, one digit, and one special character.
     */
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
     * Nuovo ID di Persona da associare all’account (opzionale).
     * - Se non fornito, la persona non viene modificata.
     * - Deve corrispondere a una Persona esistente.
     *
     * New Persona ID to associate with the account (optional).
     * - If not provided, the persona is not changed.
     * - Must correspond to an existing Persona.
     */
    @ExistingPersona(message = "personaId non corrisponde a nessuna Persona esistente / personaId does not correspond to any existing Persona")
    private Long personaId;

    /**
     * Nuovo insieme di Role IDs da assegnare all’account (opzionale).
     * - Se non fornito, i ruoli non vengono modificati.
     * - Ogni ID deve corrispondere a un ruolo esistente.
     *
     * New set of Role IDs to assign to the account (optional).
     * - If not provided, roles are not changed.
     * - Each ID must correspond to an existing role.
     */
    @ExistingRoles(message = "Almeno uno dei roleIds non è valido / At least one of the roleIds is invalid")
    private Set<Long> roleIds;

    /**
     * Nuovo stato enabled dell’account (opzionale).
     * - Solo ruoli amministrativi possono modificare questo campo.
     *
     * New enabled status for the account (optional).
     * - Only administrative roles can modify this field.
     */
    private Boolean enabled;

    /**
     * Nuovo stato emailVerified dell’account (opzionale).
     * - Solo ruoli amministrativi possono modificare questo campo.
     *
     * New emailVerified status for the account (optional).
     * - Only administrative roles can modify this field.
     */
    private Boolean emailVerified;
}
