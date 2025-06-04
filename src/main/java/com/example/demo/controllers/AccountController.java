package com.example.demo.controllers;

import com.example.demo.dto.request.AccountPasswordResetDTO;
import com.example.demo.dto.request.CreateAccountRequestDTO;
import com.example.demo.dto.request.UpdateAccountRequestDTO;
import com.example.demo.dto.response.AccountResponseDTO;
import com.example.demo.dto.response.AccountSummaryDTO;
import com.example.demo.services.interfaces.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/accounts")
public class AccountController {

    private final AccountService service;

    public AccountController(AccountService service) {
        this.service = service;
    }

    /**
     * [ITA]
     * Crea un nuovo account.
     * POST /api/accounts
     * Body: CreateAccountRequestDTO (validato via @Valid)
     * Restituisce: AccountResponseDTO con status 201 Created.
     * [ENG]
     * Create a new account.
     * POST /api/accounts
     * Body: CreateAccountRequestDTO (validated via @Valid)
     * Returns: AccountResponseDTO with status 201 Created.
     */
    @Operation(
            summary = "Crea un nuovo account / Create a new account",
            description = "Crea un account utilizzando i dati forniti nel corpo della richiesta. / Creates an account using the data provided in the request body."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Account creato con successo / Account successfully created"),
            @ApiResponse(responseCode = "400", description = "Richiesta non valida / Invalid request")
    })
    @PostMapping
    public ResponseEntity<AccountResponseDTO> createAccount(@Valid @RequestBody CreateAccountRequestDTO request) {
        AccountResponseDTO created = service.createAccount(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * [ITA]
     * Aggiorna un account esistente.
     * PUT /api/accounts/{id}
     * PathVariable: id dell’account da aggiornare
     * Body: UpdateAccountRequestDTO (validato via @Valid)
     * Restituisce: AccountResponseDTO aggiornato.
     * [ENG]
     * Update an existing account.
     * PUT /api/accounts/{id}
     * PathVariable: id of the account to update
     * Body: UpdateAccountRequestDTO (validated via @Valid)
     * Returns: Updated AccountResponseDTO.
     */
    @Operation(
            summary = "Aggiorna un account esistente / Update an existing account",
            description = "Aggiorna un account in base all'ID specificato e ai nuovi dati forniti. / Updates an account by ID using the provided data."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Account aggiornato con successo / Account successfully updated"),
            @ApiResponse(responseCode = "404", description = "Account non trovato / Account not found")
    })
    @Parameter(name = "id", description = "ID dell'account da aggiornare / ID of the account to update")
    @PutMapping("/{id}")
    public ResponseEntity<AccountResponseDTO> updateAccount(@PathVariable("id") Long id, @Valid @RequestBody UpdateAccountRequestDTO request) {
        AccountResponseDTO updated = service.updateAccount(id, request);
        return ResponseEntity.ok(updated);
    }

    /**
     * [ITA]
     * Recupera un account per ID.
     * GET /api/accounts/{id}
     * PathVariable: id dell’account
     * Restituisce: AccountResponseDTO.
     * [ENG]
     * Recover an account by ID.
     * GET /api/accounts/{id}
     * PathVariable: account id
     * Returns: AccountResponseDTO.
     */
    @Operation(
            summary = "Recupera un account per ID / Retrieve account by ID",
            description = "Restituisce l'account corrispondente all'ID fornito. / Returns the account corresponding to the given ID."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Account trovato / Account found"),
            @ApiResponse(responseCode = "404", description = "Account non trovato / Account not found")
    })
    @Parameter(name = "id", description = "ID dell'account / Account ID")
    @GetMapping("/{id}")
    public ResponseEntity<AccountResponseDTO> getAccountById(@PathVariable("id") Long id) {
        AccountResponseDTO dto = service.getAccountById(id);
        return ResponseEntity.ok(dto);
    }

    /**
     * [ITA]
     * Elimina un account per ID.
     * DELETE /api/accounts/{id}
     * PathVariable: id dell’account da cancellare
     * Restituisce: 204 No Content.
     * [ENG]
     * Delete an account by ID.
     * DELETE /api/accounts/{id}
     * PathVariable: id of the account to delete
     * Returns: 204 No Content.
     */
    @Operation(
            summary = "Elimina un account / Delete an account",
            description = "Elimina l'account corrispondente all'ID fornito. / Deletes the account with the given ID."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Account eliminato / Account deleted"),
            @ApiResponse(responseCode = "404", description = "Account non trovato / Account not found")
    })
    @Parameter(name = "id", description = "ID dell'account da eliminare / ID of the account to delete")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAccount(@PathVariable("id") Long id) {
        service.deleteAccount(id);
    }

    /**
     * [ITA]
     * Recupera tutti gli account, paginati.
     * GET /api/accounts
     * Query parameters gestiti da Pageable (page, size, sort)
     * Restituisce: Page<AccountResponseDTO>.
     * [ENG]
     * Get all accounts, paginated.
     * GET /api/accounts
     * Query parameters handled by Pageable (page, size, sort)
     * Returns: Page<AccountResponseDTO>.
     */
    @Operation(
            summary = "Recupera tutti gli account / Retrieve all accounts",
            description = "Restituisce una lista paginata di tutti gli account. / Returns a paginated list of all accounts."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista recuperata con successo / List successfully retrieved")
    })
    @Parameter(name = "page", description = "Numero pagina / Page number", example = "0")
    @Parameter(name = "size", description = "Numero elementi per pagina / Page size", example = "10")
    @Parameter(name = "sort", description = "Criterio di ordinamento / Sort criteria", example = "username,asc")
    @GetMapping
    public ResponseEntity<Page<AccountResponseDTO>> getAllAccounts(Pageable pageable) {
        Page<AccountResponseDTO> page = service.getAllAccounts(pageable);
        return ResponseEntity.ok(page);
    }

    /**
     * [ITA]
     * Blocca un account impostando lockedUntil.
     * PUT /api/accounts/{id}/lock
     * PathVariable: id dell’account
     * Body: AccountSummaryDTO (validato via @Valid; lockUntil deve essere nel futuro)
     * Restituisce: AccountResponseDTO aggiornato.
     * [ENG]
     * Lock an account by setting lockedUntil.
     * PUT /api/accounts/{id}/lock
     * PathVariable: account id
     * Body: AccountSummaryDTO (validated via @Valid; lockUntil must be in the future)
     * Returns: updated AccountResponseDTO.
     */
    @Operation(
            summary = "Blocca un account / Lock an account",
            description = "Imposta lockedUntil per bloccare temporaneamente l'account. / Sets lockedUntil to temporarily lock the account."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Account bloccato con successo / Account successfully locked"),
            @ApiResponse(responseCode = "400", description = "Dati non validi / Invalid data")
    })
    @Parameter(name = "id", description = "ID dell'account da bloccare / ID of the account to lock")
    @PutMapping("/{id}/lock")
    public ResponseEntity<AccountResponseDTO> lockAccount(@PathVariable("id") Long id, @Valid @RequestBody AccountSummaryDTO lockInfo) {
        AccountResponseDTO locked = service.lockAccount(id, lockInfo);
        return ResponseEntity.ok(locked);
    }

    /**
     * [ITA]
     * Sblocca un account azzerando lockedUntil e failedAttempts.
     * PUT /api/accounts/{id}/unlock
     * PathVariable: id dell’account
     * Restituisce: AccountResponseDTO aggiornato.
     * [ENG]
     * Unlocks an account by clearing lockedUntil and failedAttempts.
     * PUT /api/accounts/{id}/unlock
     * PathVariable: account id
     * Returns: updated AccountResponseDTO.
     */
    @Operation(
            summary = "Sblocca un account / Unlock an account",
            description = "Azzera lockedUntil e failedAttempts. / Resets lockedUntil and failedAttempts."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Account sbloccato con successo / Account successfully unlocked"),
            @ApiResponse(responseCode = "404", description = "Account non trovato / Account not found")
    })
    @Parameter(name = "id", description = "ID dell'account da sbloccare / ID of the account to unlock")
    @PutMapping("/{id}/unlock")
    public ResponseEntity<AccountResponseDTO> unlockAccount(@PathVariable("id") Long id) {
        AccountResponseDTO unlocked = service.unlockAccount(id);
        return ResponseEntity.ok(unlocked);
    }

    /**
     * [ITA]
     * Resetta la password di un account dato lo username.
     * POST /api/accounts/reset-password
     * Body: AccountPasswordResetDTO (validato via @Valid)
     * Restituisce: AccountResponseDTO con password aggiornata.
     * [ENG]
     * Resets the password of an account given the username.
     * POST /api/accounts/reset-password
     * Body: AccountPasswordResetDTO (validated via @Valid)
     * Returns: AccountResponseDTO with updated password.
     */
    @Operation(
            summary = "Resetta la password di un account / Reset account password",
            description = "Resetta la password tramite username e nuova password forniti. / Resets the password using username and new password provided."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Password aggiornata con successo / Password successfully updated"),
            @ApiResponse(responseCode = "400", description = "Dati non validi / Invalid data")
    })
    @PostMapping("/reset-password")
    public ResponseEntity<AccountResponseDTO> resetPassword(@Valid @RequestBody AccountPasswordResetDTO resetInfo) {
        AccountResponseDTO updated = service.resetPassword(resetInfo);
        return ResponseEntity.ok(updated);
    }

    /**
     * [ITA]
     * Verifica l’email di un account (setta emailVerified = true).
     * PUT /api/accounts/{id}/verify-email
     * PathVariable: id dell’account
     * Restituisce: AccountResponseDTO aggiornato.
     * [ENG]
     * Verify an account email (set emailVerified = true).
     * PUT /api/accounts/{id}/verify-email
     * PathVariable: account id
     * Returns: updated AccountResponseDTO.
     */
    @Operation(
            summary = "Verifica l'email dell'account / Verify account email",
            description = "Imposta emailVerified a true. / Sets emailVerified to true."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Email verificata con successo / Email successfully verified"),
            @ApiResponse(responseCode = "404", description = "Account non trovato / Account not found")
    })
    @Parameter(name = "id", description = "ID dell'account da verificare / ID of the account to verify")
    @PutMapping("/{id}/verify-email")
    public ResponseEntity<AccountResponseDTO> verifyEmail(@PathVariable("id") Long id) {
        AccountResponseDTO verified = service.verifyEmail(id);
        return ResponseEntity.ok(verified);
    }

    /**
     * [ITA]
     * Abilita un account (setta enabled = true).
     * PUT /api/accounts/{id}/enable
     * PathVariable: id dell’account
     * Restituisce: AccountResponseDTO aggiornato.
     * [ENG]
     * Enable an account (set enabled = true).
     * PUT /api/accounts/{id}/enable
     * PathVariable: account id
     * Returns: updated AccountResponseDTO.
     */
    @Operation(
            summary = "Abilita un account / Enable an account",
            description = "Imposta enabled a true. / Sets enabled to true."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Account abilitato / Account enabled"),
            @ApiResponse(responseCode = "404", description = "Account non trovato / Account not found")
    })
    @Parameter(name = "id", description = "ID dell'account da abilitare / ID of the account to enable")
    @PutMapping("/{id}/enable")
    public ResponseEntity<AccountResponseDTO> enableAccount(@PathVariable("id") Long id) {
        AccountResponseDTO enabled = service.enableAccount(id);
        return ResponseEntity.ok(enabled);
    }

    /**
     * [ITA]
     * Disabilita un account (setta enabled = false).
     * PUT /api/accounts/{id}/disable
     * PathVariable: id dell’account
     * Restituisce: AccountResponseDTO aggiornato.
     * [ENG]
     * Disable an account (set enabled = false).
     * PUT /api/accounts/{id}/disable
     * PathVariable: account id
     * Returns: updated AccountResponseDTO.
     */
    @Operation(
            summary = "Disabilita un account / Disable an account",
            description = "Imposta enabled a false. / Sets enabled to false."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Account disabilitato / Account disabled"),
            @ApiResponse(responseCode = "404", description = "Account non trovato / Account not found")
    })
    @Parameter(name = "id", description = "ID dell'account da disabilitare / ID of the account to disable")
    @PutMapping("/{id}/disable")
    public ResponseEntity<AccountResponseDTO> disableAccount(@PathVariable("id") Long id) {
        AccountResponseDTO disabled = service.disableAccount(id);
        return ResponseEntity.ok(disabled);
    }

    /**
     * [ITA]
     * Recupera account filtrati per personaId, paginati.
     * GET /api/accounts/by-persona/{personaId}
     * PathVariable: personaId
     * Query params gestiti da Pageable
     * Restituisce: Page<AccountResponseDTO>.
     * [ENG]
     * Retrieve accounts filtered by personaId, paginated.
     * GET /api/accounts/by-persona/{personaId}
     * PathVariable: personId
     * Query params handled by Pageable
     * Returns: Page<AccountResponseDTO>.
     */
    @Operation(
            summary = "Recupera account per persona / Get accounts by person",
            description = "Restituisce una lista paginata di account associati a una persona. / Returns paginated accounts linked to a specific person."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Account trovati / Accounts found")
    })
    @Parameter(name = "personaId", description = "ID della persona / Person ID")
    @Parameter(name = "page", description = "Numero pagina / Page number")
    @Parameter(name = "size", description = "Numero elementi per pagina / Page size")
    @Parameter(name = "sort", description = "Criterio di ordinamento / Sort criteria")
    @GetMapping("/by-persona/{personaId}")
    public ResponseEntity<Page<AccountResponseDTO>> getAccountsByPersona(@PathVariable("personaId") Long personaId, Pageable pageable) {
        Page<AccountResponseDTO> page = service.getAccountsByPersona(personaId, pageable);
        return ResponseEntity.ok(page);
    }

    /**
     * [ITA]
     * Recupera account filtrati per roleId, paginati.
     * GET /api/accounts/by-role/{roleId}
     * PathVariable: roleId
     * Query params gestiti da Pageable
     * Restituisce: Page<AccountResponseDTO>.
     * [ENG]
     * Retrieve accounts filtered by roleId, paginated.
     * GET /api/accounts/by-role/{roleId}
     * PathVariable: roleId
     * Query params handled by Pageable
     * Returns: Page<AccountResponseDTO>.
     */
    @Operation(
            summary = "Recupera account per ruolo / Get accounts by role",
            description = "Restituisce una lista paginata di account associati a un ruolo. / Returns paginated accounts linked to a specific role."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Account trovati / Accounts found")
    })
    @Parameter(name = "roleId", description = "ID del ruolo / Role ID")
    @Parameter(name = "page", description = "Numero pagina / Page number")
    @Parameter(name = "size", description = "Numero elementi per pagina / Page size")
    @Parameter(name = "sort", description = "Criterio di ordinamento / Sort criteria")
    @GetMapping("/by-role/{roleId}")
    public ResponseEntity<Page<AccountResponseDTO>> getAccountsByRole(@PathVariable("roleId") Long roleId, Pageable pageable) {
        Page<AccountResponseDTO> page = service.getAccountsByRole(roleId, pageable);
        return ResponseEntity.ok(page);
    }

    /**
     * [ITA]
     * Recupera tutti gli account attualmente bloccati (lockedUntil > now), paginati.
     * GET /api/accounts/locked
     * Query params gestiti da Pageable
     * Restituisce: Page<AccountResponseDTO>.
     * [ENG]
     * Get all currently locked accounts (lockedUntil > now), paginated.
     * GET /api/accounts/locked
     * Query params handled by Pageable
     * Returns: Page<AccountResponseDTO>.
     */
    @Operation(
            summary = "Recupera account bloccati / Get locked accounts",
            description = "Restituisce una lista paginata di account attualmente bloccati. / Returns paginated list of currently locked accounts."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Account bloccati trovati / Locked accounts found")
    })
    @Parameter(name = "page", description = "Numero pagina / Page number")
    @Parameter(name = "size", description = "Numero elementi per pagina / Page size")
    @Parameter(name = "sort", description = "Criterio di ordinamento / Sort criteria")
    @GetMapping("/locked")
    public ResponseEntity<Page<AccountResponseDTO>> getLockedAccounts(Pageable pageable) {
        Page<AccountResponseDTO> page = service.getLockedAccounts(pageable);
        return ResponseEntity.ok(page);
    }

    /**
     * [ITA]
     * Recupera un account in base allo username.
     * GET /api/accounts/by-username
     * QueryParam: username (required)
     * Restituisce: AccountResponseDTO.
     * [ENG]
     * Retrieve an account by username.
     * GET /api/accounts/by-username
     * QueryParam: username (required)
     * Returns: AccountResponseDTO.
     */
    @Operation(
            summary = "Recupera account tramite username / Get account by username",
            description = "Restituisce l'account corrispondente allo username specificato. / Returns the account matching the given username."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Account trovato / Account found"),
            @ApiResponse(responseCode = "404", description = "Account non trovato / Account not found")
    })
    @Parameter(name = "username", description = "Username dell'account da cercare / Username of the account to find", required = true)
    @GetMapping("/by-username")
    public ResponseEntity<AccountResponseDTO> getAccountByUsername(@RequestParam("username") String username) {
        AccountResponseDTO dto = service.getAccountByUsername(username);
        return ResponseEntity.ok(dto);
    }

}
