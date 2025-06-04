package com.example.demo.services;

import com.example.demo.config.exceptions.EntityNotFoundException;
import com.example.demo.config.mapper.EntityMapper;
import com.example.demo.dto.request.AccountPasswordResetDTO;
import com.example.demo.dto.request.CreateAccountRequestDTO;
import com.example.demo.dto.request.UpdateAccountRequestDTO;
import com.example.demo.dto.response.AccountResponseDTO;
import com.example.demo.dto.response.AccountSummaryDTO;
import com.example.demo.entities.Account;
import com.example.demo.entities.Persona;
import com.example.demo.entities.Role;
import com.example.demo.repositories.AccountRepository;
import com.example.demo.repositories.PersonaRepository;
import com.example.demo.repositories.RoleRepository;
import com.example.demo.services.interfaces.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Servizio che gestisce le operazioni CRUD e altre azioni sugli account.
 * Service that handles CRUD operations and other actions on accounts.
 */
@Service
@Transactional
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final PersonaRepository personaRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final EntityMapper mapper;


    /**
     * Crea un nuovo account a partire da CreateAccountRequestDTO.
     * - Verifica che Persona esista.
     * - Recupera i Role dal loro ID.
     * - Effettua l’hashing della password.
     * - Costruisce e salva l’entity Account.
     * - Restituisce il DTO di risposta.
     *
     * Creates a new account from CreateAccountRequestDTO.
     * - Verifies that the Persona exists.
     * - Retrieves the Roles by their IDs.
     * - Hashes the password.
     * - Builds and saves the Account entity.
     * - Returns the response DTO.
     *
     * @param request Dati necessari per creare un account / Data required to create an account
     * @return AccountResponseDTO DTO di risposta con i dettagli dell’account creato /
     *         Response DTO containing details of the created account
     */
    @Override
    public AccountResponseDTO createAccount(CreateAccountRequestDTO request) {
        Persona persona = personaRepository.findById(request.getPersonaId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Persona con id " + request.getPersonaId() + " non trovata"));
        Set<Role> roles = request.getRoleIds().stream()
                .map(roleId -> roleRepository.findById(roleId)
                        .orElseThrow(() -> new EntityNotFoundException(
                                "Role con id " + roleId + " non trovato")))
                .collect(Collectors.toSet());
        String encodedPassword = passwordEncoder.encode(request.getPassword());
        Account account = Account.builder()
                .username(request.getUsername())
                .passwordHash(encodedPassword)
                .persona(persona)
                .roles(roles)
                .emailVerified(false)
                .enabled(true)
                .failedAttempts(0)
                .build();
        Account saved = accountRepository.save(account);
        return mapper.toAccountResponseDto(saved);
    }

    
    /**
     * Aggiorna un account esistente:
     * - Recupera l’entity da DB, altrimenti lancia EntityNotFoundException.
     * - Applica i campi se non null/empty:
     *   * username
     *   * password (viene ri-hashata)
     *   * persona
     *   * roles
     *   * enabled, emailVerified (solo campi amministrativi)
     * - Salva e restituisce il DTO aggiornato.
     *
     * Updates an existing account:
     * - Retrieves the entity from the DB, otherwise throws EntityNotFoundException.
     * - Applies fields if not null/empty:
     *   * username
     *   * password (will be re-hashed)
     *   * persona
     *   * roles
     *   * enabled, emailVerified (administrative fields only)
     * - Saves and returns the updated DTO.
     *
     * @param id      ID dell’account da aggiornare / ID of the account to update
     * @param request Dati da aggiornare sull’account / Data to update on the account
     * @return AccountResponseDTO DTO di risposta con i dettagli aggiornati / 
     *         Response DTO containing the updated details
     */
    @Override
    public AccountResponseDTO updateAccount(Long id, UpdateAccountRequestDTO request) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Account not found with id: " + id));
        if (request.getUsername() != null && !request.getUsername().isBlank()) {
            account.setUsername(request.getUsername());
        }
        if (request.getPassword() != null && !request.getPassword().isBlank()) {
            account.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        }
        if (request.getPersonaId() != null) {
            Persona persona = personaRepository.findById(request.getPersonaId())
                    .orElseThrow(() -> new EntityNotFoundException("Persona con id " + request.getPersonaId() + " non trovata"));
            account.setPersona(persona);
        }
        if (request.getRoleIds() != null && !request.getRoleIds().isEmpty()) {
            Set<Role> roles = request.getRoleIds().stream()
                    .map(roleId -> roleRepository.findById(roleId)
                            .orElseThrow(() -> new EntityNotFoundException("Role con id " + roleId + " non trovato")))
                    .collect(Collectors.toSet());
            account.setRoles(roles);
        }
        if (request.getEnabled() != null) {
            account.setEnabled(request.getEnabled());
        }
        if (request.getEmailVerified() != null) {
            account.setEmailVerified(request.getEmailVerified());
        }
        Account updated = accountRepository.save(account);
        return mapper.toAccountResponseDto(updated);
    }

    /**
     * Recupera un account per ID.
     * Lancia EntityNotFoundException se non esiste.
     *
     * Retrieves an account by ID.
     * Throws EntityNotFoundException if it does not exist.
     *
     * @param id ID dell’account da recuperare / ID of the account to retrieve
     * @return AccountResponseDTO DTO di risposta con i dettagli dell’account / 
     *         Response DTO containing account details
     */
    @Override
    @Transactional(readOnly = true)
    public AccountResponseDTO getAccountById(Long id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Account not found with id: " + id));
        return mapper.toAccountResponseDto(account);
    }

    /**
     * Elimina un account per ID.
     * Lancia EntityNotFoundException se non esiste.
     *
     * Deletes an account by ID.
     * Throws EntityNotFoundException if it does not exist.
     *
     * @param id ID dell’account da eliminare / ID of the account to delete
     */
    @Override
    public void deleteAccount(Long id) {
        if (!accountRepository.existsById(id)) {
            throw new EntityNotFoundException("Account not found with id: " + id);
        }
        accountRepository.deleteById(id);
    }


   /**
     * Recupera tutti gli account paginati.
     * Mappa ciascun Account in AccountResponseDTO.
     *
     * Retrieves all accounts in a paginated manner.
     * Maps each Account to AccountResponseDTO.
     *
     * @param pageable Parametri di paginazione / Pagination parameters
     * @return Page<AccountResponseDTO> Pagina di DTO con i dettagli degli account / 
     *         Page of DTOs containing account details
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AccountResponseDTO> getAllAccounts(Pageable pageable) {
        return accountRepository.findAll(pageable)
                .map(mapper::toAccountResponseDto);
    }

   /**
     * Blocca l’account impostando lockedUntil.
     * Aggiorna lo stato e restituisce il DTO aggiornato.
     *
     * Locks the account by setting lockedUntil.
     * Updates the state and returns the updated DTO.
     *
     * @param id       ID dell’account da bloccare / ID of the account to lock
     * @param lockInfo DTO contenente la data/ora di sblocco / DTO containing the unlock date/time
     * @return AccountResponseDTO DTO di risposta con i dettagli aggiornati / 
     *         Response DTO containing updated details
     * @throws IllegalArgumentException Se lockUntil è nel passato / If lockUntil is in the past
     */
    @Override
    public AccountResponseDTO lockAccount(Long id, AccountSummaryDTO lockInfo) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Account not found with id: " + id));
        Instant now = Instant.now();
        if (lockInfo.getLockUntil().isBefore(now)) {
            throw new IllegalArgumentException("lockUntil must be in the future");
        }
        account.setLockedUntil(lockInfo.getLockUntil());
        Account updated = accountRepository.save(account);
        return mapper.toAccountResponseDto(updated);
    }

     /**
     * Sblocca l’account azzerando lockedUntil, failedAttempts e aggiornando updatedAt automaticamente.
     *
     * Unlocks the account by resetting lockedUntil and failedAttempts, and automatically updates updatedAt.
     *
     * @param id ID dell’account da sbloccare / ID of the account to unlock
     * @return AccountResponseDTO DTO di risposta con i dettagli aggiornati / 
     *         Response DTO containing updated details
     */
    @Override
    public AccountResponseDTO unlockAccount(Long id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Account not found with id: " + id));
        account.setLockedUntil(null);
        account.setFailedAttempts(0);
        Account updated = accountRepository.save(account);
        return mapper.toAccountResponseDto(updated);
    }

   /**
     * Resetta la password di un account dato lo username:
     * - Ricerca account per username.
     * - Imposta nuova password hashata.
     * - Azzera failedAttempts e lockedUntil.
     * - EmailVerified ed enabled rimangono invariati.
     *
     * Resets the password of an account given the username:
     * - Searches for the account by username.
     * - Sets a new hashed password.
     * - Resets failedAttempts and lockedUntil.
     * - emailVerified and enabled remain unchanged.
     *
     * @param resetInfo DTO contenente username e nuova password / DTO containing username and new password
     * @return AccountResponseDTO DTO di risposta con i dettagli aggiornati / 
     *         Response DTO containing updated details
     */
    @Override
    public AccountResponseDTO resetPassword(AccountPasswordResetDTO resetInfo) {
        Account account = accountRepository.findAll().stream()
                .filter(a -> a.getUsername().equals(resetInfo.getUsername()))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Account with username" + resetInfo.getUsername() + "not found"));
        account.setPasswordHash(passwordEncoder.encode(resetInfo.getNewPassword()));
        account.setFailedAttempts(0);
        account.setLockedUntil(null);
        Account updated = accountRepository.save(account);
        return mapper.toAccountResponseDto(updated);
    }

    /**
     * Imposta emailVerified = true.
     *
     * Sets emailVerified = true.
     *
     * @param id ID dell’account da verificare l’email / ID of the account to verify email
     * @return AccountResponseDTO DTO di risposta con i dettagli aggiornati / 
     *         Response DTO containing updated details
     */
    @Override
    public AccountResponseDTO verifyEmail(Long id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Account not found with id: " + id));
        account.setEmailVerified(true);
        Account updated = accountRepository.save(account);
        return mapper.toAccountResponseDto(updated);
    }

   /**
     * Imposta enabled = true.
     *
     * Sets enabled = true.
     *
     * @param id ID dell’account da abilitare / ID of the account to enable
     * @return AccountResponseDTO DTO di risposta con i dettagli aggiornati / 
     *         Response DTO containing updated details
     */
    @Override
    public AccountResponseDTO enableAccount(Long id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Account not found with id: " + id));
        account.setEnabled(true);
        Account updated = accountRepository.save(account);
        return mapper.toAccountResponseDto(updated);
    }

    /**
     * Imposta enabled = false.
     *
     * Sets enabled = false.
     *
     * @param id ID dell’account da disabilitare / ID of the account to disable
     * @return AccountResponseDTO DTO di risposta con i dettagli aggiornati / 
     *         Response DTO containing updated details
     */
    @Override
    public AccountResponseDTO disableAccount(Long id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Account not found with id: " + id));
        account.setEnabled(false);
        Account updated = accountRepository.save(account);
        return mapper.toAccountResponseDto(updated);
    }

   /**
     * Recupera account filtrati per personaId (paginati).
     *
     * Retrieves accounts filtered by personaId (paginated).
     *
     * @param personaId ID della Persona per filtrare gli account / ID of the Persona to filter accounts
     * @param pageable  Parametri di paginazione / Pagination parameters
     * @return Page<AccountResponseDTO> Pagina di DTO con i dettagli degli account filtrati / 
     *         Page of DTOs containing filtered account details
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AccountResponseDTO> getAccountsByPersona(Long personaId, Pageable pageable) {
        Persona persona = personaRepository.findById(personaId)
                .orElseThrow(() -> new EntityNotFoundException("Persona not found with id: " + personaId));
        return (Page<AccountResponseDTO>) accountRepository.findAll(pageable)
                .filter(account -> account.getPersona().getId().equals(persona.getId()))
                .map(mapper::toAccountResponseDto);
    }
  /**
     * Recupera account filtrati per roleId (paginati).
     *
     * Retrieves accounts filtered by roleId (paginated).
     *
     * @param roleId   ID del Role per filtrare gli account / ID of the Role to filter accounts
     * @param pageable Parametri di paginazione / Pagination parameters
     * @return Page<AccountResponseDTO> Pagina di DTO con i dettagli degli account filtrati / 
     *         Page of DTOs containing filtered account details
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AccountResponseDTO> getAccountsByRole(Long roleId, Pageable pageable) {
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new EntityNotFoundException("Role not found with id: " + roleId));
        return (Page<AccountResponseDTO>) accountRepository.findAll(pageable)
                .filter(account -> account.getRoles().contains(role))
                .map(mapper::toAccountResponseDto);
    }

   /**
     * Recupera gli account attualmente bloccati (lockedUntil > now).
     *
     * Retrieves accounts that are currently locked (lockedUntil > now).
     *
     * @param pageable Parametri di paginazione / Pagination parameters
     * @return Page<AccountResponseDTO> Pagina di DTO con i dettagli degli account bloccati / 
     *         Page of DTOs containing locked account details
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AccountResponseDTO> getLockedAccounts(Pageable pageable) {
        Instant now = Instant.now();
        return (Page<AccountResponseDTO>) accountRepository.findAll(pageable)
                .filter(account -> account.getLockedUntil() != null && account.getLockedUntil().isAfter(now))
                .map(mapper::toAccountResponseDto);
    }

    /**
     * Recupera un account per username.
     *
     * Retrieves an account by username.
     *
     * @param username Username dell’account da recuperare / Username of the account to retrieve
     * @return AccountResponseDTO DTO di risposta con i dettagli dell’account / 
     *         Response DTO containing account details
     */
    @Override
    @Transactional(readOnly = true)
    public AccountResponseDTO getAccountByUsername(String username) {
        Account account = accountRepository.findAll().stream()
                .filter(a -> a.getUsername().equals(username))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Account con username " + username + " non trovato"));
        return mapper.toAccountResponseDto(account);
    }

}
