package com.example.demo.services.interfaces;


import com.example.demo.dto.request.AccountPasswordResetDTO;
import com.example.demo.dto.request.CreateAccountRequestDTO;
import com.example.demo.dto.request.UpdateAccountRequestDTO;
import com.example.demo.dto.response.AccountResponseDTO;
import com.example.demo.dto.response.AccountSummaryDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


/**
 * Interfaccia del servizio per la gestione degli account.
 * Definisce le operazioni CRUD di base, le azioni specifiche del dominio e le query per gli account.
 *
 * Interface for account management service.
 * Defines basic CRUD operations, domain-specific actions, and account queries.
 */
public interface AccountService {

    /**
     * Crea un nuovo account a partire da CreateAccountRequestDTO.
     *
     * Creates a new account from CreateAccountRequestDTO.
     *
     * @param request DTO contenente i dati necessari per la creazione dell’account
     *                DTO containing the data required to create the account
     * @return AccountResponseDTO DTO di risposta con i dettagli dell’account creato
     *         Response DTO containing details of the created account
     */
    AccountResponseDTO createAccount(CreateAccountRequestDTO request);

    /**
     * Aggiorna un account esistente.
     *
     * Updates an existing account.
     *
     * @param id      ID dell’account da aggiornare / ID of the account to update
     * @param request DTO contenente i campi da aggiornare / DTO containing fields to update
     * @return AccountResponseDTO DTO di risposta con i dettagli aggiornati dell’account
     *         Response DTO containing updated account details
     */
    AccountResponseDTO updateAccount(Long id, UpdateAccountRequestDTO request);

    /**
     * Recupera un account per ID.
     *
     * Retrieves an account by its ID.
     *
     * @param id ID dell’account da recuperare / ID of the account to retrieve
     * @return AccountResponseDTO DTO di risposta con i dettagli dell’account
     *         Response DTO containing account details
     */
    AccountResponseDTO getAccountById(Long id);

    /**
     * Elimina un account per ID.
     *
     * Deletes an account by its ID.
     *
     * @param id ID dell’account da eliminare / ID of the account to delete
     */
    void deleteAccount(Long id);

    /**
     * Recupera tutti gli account in modo paginato.
     *
     * Retrieves all accounts in a paginated fashion.
     *
     * @param pageable Parametri di paginazione (numero di pagina, dimensione, ordinamento) /
     *                 Pagination parameters (page number, size, sorting)
     * @return Page<AccountResponseDTO> Pagina di DTO con i dettagli di tutti gli account /
     *         Page of DTOs containing details of all accounts
     */
    Page<AccountResponseDTO> getAllAccounts(Pageable pageable);

     /**
     * Blocca un account impostando la data/ora di sblocco (lockedUntil).
     *
     * Locks an account by setting the unlock date/time (lockedUntil).
     *
     * @param id       ID dell’account da bloccare / ID of the account to lock
     * @param lockInfo DTO contenente la data/ora di sblocco / DTO containing unlock date/time
     * @return AccountResponseDTO DTO di risposta con i dettagli aggiornati dell’account bloccato /
     *         Response DTO containing updated details of the locked account
     */
    AccountResponseDTO lockAccount(Long id, AccountSummaryDTO lockInfo);

     /**
     * Sblocca un account azzerando lockedUntil e failedAttempts.
     *
     * Unlocks an account by resetting lockedUntil and failedAttempts.
     *
     * @param id ID dell’account da sbloccare / ID of the account to unlock
     * @return AccountResponseDTO DTO di risposta con i dettagli aggiornati dell’account sbloccato /
     *         Response DTO containing updated details of the unlocked account
     */
    AccountResponseDTO unlockAccount(Long id);

      /**
     * Resetta la password di un account dato lo username:
     * - Imposta nuova password hashata.
     * - Azzera failedAttempts e lockedUntil.
     * - Mantiene invariati emailVerified ed enabled.
     *
     * Resets the password of an account given the username:
     * - Sets new hashed password.
     * - Resets failedAttempts and lockedUntil.
     * - Keeps emailVerified and enabled unchanged.
     *
     * @param resetInfo DTO contenente username e nuova password / DTO containing username and new password
     * @return AccountResponseDTO DTO di risposta con i dettagli aggiornati dell’account /
     *         Response DTO containing updated account details
     */
    AccountResponseDTO resetPassword(AccountPasswordResetDTO resetInfo);

     /**
     * Imposta emailVerified = true per un account.
     *
     * Sets emailVerified = true for an account.
     *
     * @param id ID dell’account da verificare l’email / ID of the account to verify email
     * @return AccountResponseDTO DTO di risposta con i dettagli aggiornati dell’account /
     *         Response DTO containing updated account details
     */
    AccountResponseDTO verifyEmail(Long id);

    /**
     * Imposta enabled = true per un account.
     *
     * Sets enabled = true for an account.
     *
     * @param id ID dell’account da abilitare / ID of the account to enable
     * @return AccountResponseDTO DTO di risposta con i dettagli aggiornati dell’account /
     *         Response DTO containing updated account details
     */
    AccountResponseDTO enableAccount(Long id);

    /**
     * Imposta enabled = false per un account.
     *
     * Sets enabled = false for an account.
     *
     * @param id ID dell’account da disabilitare / ID of the account to disable
     * @return AccountResponseDTO DTO di risposta con i dettagli aggiornati dell’account /
     *         Response DTO containing updated account details
     */
    AccountResponseDTO disableAccount(Long id);

    /**
     * Recupera account filtrati per personaId in modo paginato.
     *
     * Retrieves accounts filtered by personaId in a paginated fashion.
     *
     * @param personaId ID della Persona per cui filtrare gli account / ID of the Persona to filter accounts
     * @param pageable  Parametri di paginazione (numero di pagina, dimensione, ordinamento) /
     *                  Pagination parameters (page number, size, sorting)
     * @return Page<AccountResponseDTO> Pagina di DTO con i dettagli degli account filtrati per persona /
     *         Page of DTOs containing details of accounts filtered by persona
     */
    Page<AccountResponseDTO> getAccountsByPersona(Long personaId, Pageable pageable);

     /**
     * Recupera account filtrati per roleId in modo paginato.
     *
     * Retrieves accounts filtered by roleId in a paginated fashion.
     *
     * @param roleId   ID del Role per cui filtrare gli account / ID of the Role to filter accounts
     * @param pageable Parametri di paginazione (numero di pagina, dimensione, ordinamento) /
     *                 Pagination parameters (page number, size, sorting)
     * @return Page<AccountResponseDTO> Pagina di DTO con i dettagli degli account filtrati per ruolo /
     *         Page of DTOs containing details of accounts filtered by role
     */
    Page<AccountResponseDTO> getAccountsByRole(Long roleId, Pageable pageable);

    /**
     * Recupera gli account attualmente bloccati (lockedUntil > ora corrente) in modo paginato.
     *
     * Retrieves accounts that are currently locked (lockedUntil > now) in a paginated fashion.
     *
     * @param pageable Parametri di paginazione (numero di pagina, dimensione, ordinamento) /
     *                 Pagination parameters (page number, size, sorting)
     * @return Page<AccountResponseDTO> Pagina di DTO con i dettagli degli account bloccati /
     *         Page of DTOs containing details of locked accounts
     */
    Page<AccountResponseDTO> getLockedAccounts(Pageable pageable);

    /**
     * Recupera un account per username.
     *
     * Retrieves an account by username.
     *
     * @param username Username dell’account da recuperare / Username of the account to retrieve
     * @return AccountResponseDTO DTO di risposta con i dettagli dell’account /
     *         Response DTO containing account details
     */
    AccountResponseDTO getAccountByUsername(String username);


}