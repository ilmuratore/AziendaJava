package com.example.demo.controllers;


import com.example.demo.dto.request.LoginRequestDTO;
import com.example.demo.dto.response.LoginResponseDTO;
import com.example.demo.config.security.LoginService;
import io.swagger.v3.oas.annotations.Operation;
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
@RequestMapping("api/auth")
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
    public AuthController(LoginService service){ this.service = service;}


    /**
     * Effettua il login utilizzando username e password.
     *
     * <p><strong>English:</strong> Performs login using username and password.</p>
     *
     * @param request DTO contenente username e password
     *                <p><strong>English:</strong> DTO containing username and password.</p>
     * @return {@code ResponseEntity} con {@link LoginResponseDTO} e stato HTTP 200 (OK)
     *         <p><strong>English:</strong> {@code ResponseEntity} with {@link LoginResponseDTO} and HTTP status 200 (OK).</p>
     */
    @Operation(
            summary = "Effettua il login con username e password / Perform login with username and password",
            description = "Richiede username e password, restituisce token JWT e dettagli account se le credenziali sono valide. / "
                    + "Requires username and password, returns JWT token and account details if credentials are valid."
    )
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginRequestDTO request){
        LoginResponseDTO response = service.login(request);
        return ResponseEntity.ok(response);
    }


}
