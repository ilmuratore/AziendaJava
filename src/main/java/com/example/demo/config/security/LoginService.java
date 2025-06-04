package com.example.demo.config.security;


import com.example.demo.config.exceptions.EntityNotFoundException;
import com.example.demo.config.exceptions.FailedLoginException;
import com.example.demo.config.mapper.EntityMapper;
import com.example.demo.dto.request.LoginRequestDTO;
import com.example.demo.dto.response.LoginResponseDTO;
import com.example.demo.entities.Account;
import com.example.demo.repositories.AccountRepository;
import lombok.RequiredArgsConstructor;
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
 * l’autenticazione, l’aggiornamento dei tentativi falliti
 * e la generazione del token JWT.
 *
 * <p><strong>English:</strong> Service handling the login process, including
 * authentication, updating failed attempts count, and generating JWT token.</p>
 *
 * <p>Procedura:</p>
 * <ol>
 *     <li>Autentica le credenziali tramite {@link AuthenticationManager}.</li>
 *     <li>In caso di fallimento di autenticazione, incrementa il contatore
 *         dei tentativi falliti e lancia {@link BadCredentialsException}.</li>
 *     <li>Se l’utente esiste, resetta i tentativi falliti, aggiorna il timestamp
 *         di ultimo login e salva l’entità {@link Account}.</li>
 *     <li>Estrae i nomi dei ruoli, genera un token JWT tramite {@link JwtUtil} e
 *         ritorna un {@link LoginResponseDTO} mappato con {@link EntityMapper}.</li>
 * </ol>
 *
 * @see AuthenticationManager
 * @see JwtUtil
 * @see EntityMapper
 */
@Service
@Transactional
@RequiredArgsConstructor
public class LoginService {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final EntityMapper mapper;

    /**
     * Esegue il login per un utente dato un {@link LoginRequestDTO}.
     *
     * <p><strong>English:</strong> Performs login for a user given a {@link LoginRequestDTO}.</p>
     *
     * <p>Passaggi:</p>
     * <ol>
     *     <li>Tenta di autenticare le credenziali tramite
     *         {@link AuthenticationManager#authenticate(Authentication)}.</li>
     *     <li>Se l’autenticazione fallisce, incrementa
     *         {@code failedAttempts} nel record {@link Account} (se esiste) e lancia
     *         {@link BadCredentialsException}.</li>
     *     <li>Recupera l’oggetto {@link Account} dal database;
     *         se non esiste, lancia {@link EntityNotFoundException}.</li>
     *     <li>Resetta {@code failedAttempts}, aggiorna {@code lastLogin} con timestamp corrente
     *         e salva l’entità aggiornata.</li>
     *     <li>Estrae i nomi dei ruoli, genera token JWT tramite {@link JwtUtil#generateToken(String, Set)},
     *         quindi mappa l’account e il token in {@link LoginResponseDTO} tramite {@link EntityMapper}.</li>
     * </ol>
     *
     * @param loginRequest DTO contenente username e password dell’utente
     *                     <p><strong>English:</strong> DTO containing user's username and password.</p>
     * @return {@link LoginResponseDTO} con i dati dell’account e il token JWT
     *         <p><strong>English:</strong> {@link LoginResponseDTO} containing account data and the JWT token.</p>
     * @throws BadCredentialsException     se l’autenticazione fallisce (credenziali errate)
     *                                     <p><strong>English:</strong> if authentication fails (invalid credentials).</p>
     * @throws EntityNotFoundException     se l’account con lo username specificato non esiste
     *                                     <p><strong>English:</strong> if account with given username is not found.</p>
     */
    public LoginResponseDTO login(LoginRequestDTO loginRequest){
        try{
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword()
                    )
            );
        } catch (AuthenticationException ex){
            Account account = accountRepository.findByUsername(loginRequest.getUsername())
                    .orElse(null);
            if (account != null) {
                int attuali = account.getFailedAttempts() != null ? account.getFailedAttempts() : 0;
                account.setFailedAttempts(attuali + 1);
                accountRepository.save(account);
            }
            throw new BadCredentialsException("Invalid credential");
        }
        Account account = accountRepository.findByUsername(loginRequest.getUsername())
                .orElseThrow( () -> new EntityNotFoundException("Account with username \"" + loginRequest.getUsername() + "\" not found"));

        account.setFailedAttempts(0);
        account.setLastLogin(Instant.now());
        Account updated = accountRepository.save(account);
        Set<String> roleNames = updated.getRoles().stream().map(role -> role.getName()).collect(Collectors.toSet());
        String token = jwtUtil.generateToken(updated.getUsername(), roleNames);
        return mapper.toLoginResponseDto(updated, token);
    }

}
