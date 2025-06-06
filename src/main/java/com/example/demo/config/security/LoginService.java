package com.example.demo.config.security;

import com.example.demo.config.exceptions.EntityNotFoundException;
import com.example.demo.config.mapper.EntityMapper;
import com.example.demo.dto.request.LoginRequestDTO;
import com.example.demo.dto.response.LoginResponseDTO;
import com.example.demo.entities.Account;
import com.example.demo.repositories.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Servizio per la gestione del processo di login, inclusa
 * l'autenticazione, l'aggiornamento dei tentativi falliti
 * e la generazione del token JWT.
 *
 * <p><strong>English:</strong> Service handling the login process, including
 * authentication, updating failed attempts count, and generating JWT token.</p>
 */
@Service
@Transactional
@RequiredArgsConstructor
public class LoginService {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    @Lazy // Risolve la dipendenza circolare
    private final AuthenticationManager authenticationManager;
    private final EntityMapper mapper;

    /**
     * Esegue il login per un utente dato un {@link LoginRequestDTO}.
     *
     * <p><strong>English:</strong> Performs login for a user given a {@link LoginRequestDTO}.</p>
     */
    public LoginResponseDTO login(LoginRequestDTO loginRequest) {
        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword()
                    )
            );
        } catch (AuthenticationException ex) {
            // Incrementa i tentativi falliti se l'account esiste
            Account account = accountRepository.findByUsername(loginRequest.getUsername())
                    .orElse(null);
            if (account != null) {
                int attuali = account.getFailedAttempts() != null ? account.getFailedAttempts() : 0;
                account.setFailedAttempts(attuali + 1);
                accountRepository.save(account);
            }
            throw new BadCredentialsException("Invalid credentials");
        }

        // Login riuscito - aggiorna l'account
        Account account = accountRepository.findByUsername(loginRequest.getUsername())
                .orElseThrow(() -> new EntityNotFoundException("Account with username \"" + loginRequest.getUsername() + "\" not found"));

        account.setFailedAttempts(0);
        account.setLastLogin(Instant.now());
        Account updated = accountRepository.save(account);

        Set<String> roleNames = updated.getRoles().stream()
                .map(role -> role.getName())
                .collect(Collectors.toSet());

        String token = jwtUtil.generateToken(updated.getUsername(), roleNames);
        return mapper.toLoginResponseDto(updated, token);
    }
}