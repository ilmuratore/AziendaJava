package com.example.demo.config.security;

import com.example.demo.entities.Account;
import com.example.demo.repositories.AccountRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.stream.Collectors;

/**
 * Servizio personalizzato per caricare i dettagli dell’utente richiesti da Spring Security.
 *
 * <p><strong>English:</strong> Custom service to load user details required by Spring Security.</p>
 * <p><strong>Italiano:</strong> Servizio personalizzato per caricare i dettagli dell’utente richiesti da Spring Security.</p>
 *
 * <p>Implementa {@link UserDetailsService} per cercare un {@link Account} nel database,
 * verificare lo stato dell’account e restituire un oggetto {@link UserDetails} con le
 * autorità corrispondenti.</p>
 * <p><strong>English:</strong> Implements {@link UserDetailsService} to lookup an {@link Account}
 * in the database, verify account status, and return a {@link UserDetails} object with appropriate
 * authorities.</p>
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final AccountRepository accountRepository;

    /**
     * Costruttore iniettato con {@link AccountRepository}.
     *
     * <p><strong>English:</strong> Constructor injected with {@link AccountRepository}.</p>
     * <p><strong>Italiano:</strong> Costruttore iniettato con {@link AccountRepository}.</p>
     *
     * @param accountRepository repository per le operazioni CRUD su {@link Account}
     *                          <p><strong>English:</strong> repository for CRUD operations on {@link Account}.</p>
     *                          <p><strong>Italiano:</strong> repository per operazioni CRUD su {@link Account}.</p>
     */
    public CustomUserDetailsService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    /**
     * Carica i dettagli dell’utente dato uno username.
     *
     * <p><strong>English:</strong> Loads user details given a username.</p>
     * <p><strong>Italiano:</strong> Carica i dettagli dell’utente dato uno username.</p>
     *
     * <p>Procedura:</p>
     * <ol>
     *     <li>Cerca {@link Account} tramite {@link AccountRepository#findByUsername(String)}.</li>
     *     <li>Se non trovato, lancia {@link UsernameNotFoundException} con messaggio adeguato.</li>
     *     <li>Verifica se l’account è abilitato ({@link Account#getEnabled()}); altrimenti lancia {@link UsernameNotFoundException}.</li>
     *     <li>Verifica se l’email è verificata ({@link Account#getEmailVerified()}); altrimenti lancia {@link UsernameNotFoundException}.</li>
     *     <li>Crea una lista di {@link SimpleGrantedAuthority} mappando i ruoli di {@link Account}.</li>
     *     <li>Restituisce un oggetto {@link User} (implementazione di {@link UserDetails}) con username, password, autorità e stato di blocco.</li>
     * </ol>
     *
     * <p><strong>English:</strong> Steps:</p>
     * <ol>
     *     <li>Find {@link Account} via {@link AccountRepository#findByUsername(String)}.</li>
     *     <li>If not found, throw {@link UsernameNotFoundException} with a descriptive message.</li>
     *     <li>Check if account is enabled ({@link Account#getEnabled()}); otherwise throw {@link UsernameNotFoundException}.</li>
     *     <li>Check if email is verified ({@link Account#getEmailVerified()}); otherwise throw {@link UsernameNotFoundException}.</li>
     *     <li>Create a list of {@link SimpleGrantedAuthority} by mapping {@link Account}'s roles.</li>
     *     <li>Return a {@link User} (implementation of {@link UserDetails}) with username, password, authorities, and lock status.</li>
     * </ol>
     *
     * @param username stringa che identifica univocamente l’account dell’utente
     *                 <p><strong>English:</strong> string that uniquely identifies the user’s account.</p>
     *                 <p><strong>Italiano:</strong> stringa che identifica univocamente l’account dell’utente.</p>
     * @return {@link UserDetails} istanza contenente username, password hash, autorità e stato dell’account
     *         <p><strong>English:</strong> {@link UserDetails} instance containing username, password hash, authorities, and account state.</p>
     *         <p><strong>Italiano:</strong> istanza di {@link UserDetails} contenente username, password hash, autorità e stato dell’account.</p>
     * @throws UsernameNotFoundException se l’account non esiste, è disabilitato o l’email non è verificata
     *                                   <p><strong>English:</strong> if account does not exist, is disabled, or email is not verified.</p>
     *                                   <p><strong>Italiano:</strong> se l’account non esiste, è disabilitato o l’email non è verificata.</p>
     */
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountRepository.findByUsername(username)
                .orElseThrow( ()-> new UsernameNotFoundException("No user found with username: " + username));
        if(Boolean.FALSE.equals(account.getEnabled())){
            throw new UsernameNotFoundException("Account disabled");
        }
        if(Boolean.FALSE.equals(account.getEmailVerified())){
            throw new UsernameNotFoundException("Email not verified");
        }
        var authorities = account.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());

        return User.withUsername(account.getUsername())
                .password(account.getPasswordHash())
                .authorities(authorities)
                .accountLocked(account.getLockedUntil() != null && account.getLockedUntil().isAfter(Instant.now()))
                .build();
    }
}
