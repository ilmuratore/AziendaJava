package com.example.demo.dto.request;

import com.example.demo.config.validation.ExistingPersona;
import com.example.demo.config.validation.ExistingRoles;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;


@Getter
@Setter
@Schema(name = "UpdateAccountRequest", description = "Payload per effettuare aggiornamento account | Payload to update an account")
public class UpdateAccountRequestDTO {

    @Size(min = 3, max = 50, message = "username deve avere tra 3 e 50 caratteri / username must be between 3 and 50 characters")
    @Pattern(regexp = "^[a-zA-Z0-9._-]+$", message = "username può contenere solo lettere, numeri, punti, underscore e trattini / username can contain only letters, numbers, dots, underscores, and hyphens")
    private String username;

    @Size(min = 8, max = 100, message = "password deve avere tra 8 e 100 caratteri / password must be between 8 and 100 characters")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&].*$", message = "password deve contenere almeno una lettera minuscola, maiuscola, numero e carattere speciale / password must contain at least one lowercase letter, one uppercase letter, one digit, and one special character")
    private String password;

    @ExistingPersona(message = "personaId non corrisponde a nessuna Persona esistente / personaId does not correspond to any existing Persona")
    private Long personaId;

    @ExistingRoles(message = "Almeno uno dei roleIds non è valido / At least one of the roleIds is invalid")
    private Set<Long> roleIds;

    private Boolean enabled;
    private Boolean emailVerified;
}
