package com.example.demo.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Schema(name = "LoginAccountRequest", description = "Payload per effettuare accesso tramite account | Payload to login an account")
public class LoginRequestDTO {

    @Schema(description = "Username dell'account | Account username", example = "mario.rossi", required = true)
    @NotBlank(message = "Username non può essere vuoto | Username cannot be blank")
    private String username;

    @Schema(description = "Password in chiaro | Plain text password", example = "P@ssw0rd!", required = true)
    @NotBlank(message = "Password non può essere vuota | Password cannot be blank")
    private String password;
}
