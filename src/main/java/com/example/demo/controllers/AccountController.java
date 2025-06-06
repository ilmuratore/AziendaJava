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
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Accounts", description = "Controller per la gestione degli Account | Eng da aggiungere")
@RestController
@RequestMapping("api/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService service;

    @Operation(summary = "Crea un nuovo account | Create a new account", description = "Crea un account utilizzando i dati forniti nel corpo della richiesta. | Creates an account using the data provided in the request body.")
    @ApiResponses({@ApiResponse(responseCode = "201", description = "Account creato con successo | Account successfully created"), @ApiResponse(responseCode = "400", description = "Richiesta non valida | Invalid request")})
    @PostMapping
    public ResponseEntity<AccountResponseDTO> createAccount(@Valid @RequestBody CreateAccountRequestDTO request) {
        AccountResponseDTO created = service.createAccount(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @Operation(summary = "Aggiorna un account esistente | Update an existing account", description = "Aggiorna un account in base all'ID specificato e ai nuovi dati forniti. | Updates an account by ID using the provided data.")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Account aggiornato con successo | Account successfully updated"), @ApiResponse(responseCode = "404", description = "Account non trovato | Account not found")})
    @Parameter(name = "id", description = "ID dell'account da aggiornare | ID of the account to update")
    @PutMapping("/{id}")
    public ResponseEntity<AccountResponseDTO> updateAccount(@PathVariable("id") Long id, @Valid @RequestBody UpdateAccountRequestDTO request) {
        AccountResponseDTO updated = service.updateAccount(id, request);
        return ResponseEntity.ok(updated);
    }

    @Operation(summary = "Recupera un account per ID | Retrieve account by ID", description = "Restituisce l'account corrispondente all'ID fornito. | Returns the account corresponding to the given ID.")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Account trovato | Account found"), @ApiResponse(responseCode = "404", description = "Account non trovato | Account not found")})
    @Parameter(name = "id", description = "ID dell'account | Account ID")
    @GetMapping("/{id}")
    public ResponseEntity<AccountResponseDTO> getAccountById(@PathVariable("id") Long id) {
        AccountResponseDTO dto = service.getAccountById(id);
        return ResponseEntity.ok(dto);
    }

    @Operation(summary = "Elimina un account | Delete an account", description = "Elimina l'account corrispondente all'ID fornito. | Deletes the account with the given ID.")
    @ApiResponses({@ApiResponse(responseCode = "204", description = "Account eliminato | Account deleted"), @ApiResponse(responseCode = "404", description = "Account non trovato | Account not found")})
    @Parameter(name = "id", description = "ID dell'account da eliminare | ID of the account to delete")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAccount(@PathVariable("id") Long id) {
        service.deleteAccount(id);
    }

    @Operation(summary = "Recupera tutti gli account | Retrieve all accounts", description = "Restituisce una lista paginata di tutti gli account. | Returns a paginated list of all accounts.")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Lista recuperata con successo | List successfully retrieved")})
    @Parameter(name = "page", description = "Numero pagina / Page number", example = "0")
    @Parameter(name = "size", description = "Numero elementi per pagina | Page size", example = "10")
    @Parameter(name = "sort", description = "Criterio di ordinamento | Sort criteria", example = "username,asc")
    @GetMapping
    public ResponseEntity<Page<AccountResponseDTO>> getAllAccounts(Pageable pageable) {
        Page<AccountResponseDTO> page = service.getAllAccounts(pageable);
        return ResponseEntity.ok(page);
    }

    @Operation(summary = "Blocca un account / Lock an account", description = "Imposta lockedUntil per bloccare temporaneamente l'account. | Sets lockedUntil to temporarily lock the account.")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Account bloccato con successo | Account successfully locked"), @ApiResponse(responseCode = "400", description = "Dati non validi | Invalid data")})
    @Parameter(name = "id", description = "ID dell'account da bloccare | ID of the account to lock")
    @PutMapping("/{id}/lock")
    public ResponseEntity<AccountResponseDTO> lockAccount(@PathVariable("id") Long id, @Valid @RequestBody AccountSummaryDTO lockInfo) {
        AccountResponseDTO locked = service.lockAccount(id, lockInfo);
        return ResponseEntity.ok(locked);
    }

    @Operation(summary = "Sblocca un account / Unlock an account", description = "Azzera lockedUntil e failedAttempts. | Resets lockedUntil and failedAttempts.")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Account sbloccato con successo | Account successfully unlocked"), @ApiResponse(responseCode = "404", description = "Account non trovato | Account not found")})
    @Parameter(name = "id", description = "ID dell'account da sbloccare | ID of the account to unlock")
    @PutMapping("/{id}/unlock")
    public ResponseEntity<AccountResponseDTO> unlockAccount(@PathVariable("id") Long id) {
        AccountResponseDTO unlocked = service.unlockAccount(id);
        return ResponseEntity.ok(unlocked);
    }

    @Operation(summary = "Resetta la password di un account | Reset account password", description = "Resetta la password tramite username e nuova password forniti. | Resets the password using username and new password provided.")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Password aggiornata con successo | Password successfully updated"), @ApiResponse(responseCode = "400", description = "Dati non validi | Invalid data")})
    @PostMapping("/reset-password")
    public ResponseEntity<AccountResponseDTO> resetPassword(@Valid @RequestBody AccountPasswordResetDTO resetInfo) {
        AccountResponseDTO updated = service.resetPassword(resetInfo);
        return ResponseEntity.ok(updated);
    }

    @Operation(summary = "Verifica l'email dell'account | Verify account email", description = "Imposta emailVerified a true. | Sets emailVerified to true.")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Email verificata con successo | Email successfully verified"), @ApiResponse(responseCode = "404", description = "Account non trovato | Account not found")})
    @Parameter(name = "id", description = "ID dell'account da verificare | ID of the account to verify")
    @PutMapping("/{id}/verify-email")
    public ResponseEntity<AccountResponseDTO> verifyEmail(@PathVariable("id") Long id) {
        AccountResponseDTO verified = service.verifyEmail(id);
        return ResponseEntity.ok(verified);
    }

    @Operation(summary = "Abilita un account | Enable an account", description = "Imposta enabled a true. | Sets enabled to true.")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Account abilitato | Account enabled"), @ApiResponse(responseCode = "404", description = "Account non trovato | Account not found")})
    @Parameter(name = "id", description = "ID dell'account da abilitare | ID of the account to enable")
    @PutMapping("/{id}/enable")
    public ResponseEntity<AccountResponseDTO> enableAccount(@PathVariable("id") Long id) {
        AccountResponseDTO enabled = service.enableAccount(id);
        return ResponseEntity.ok(enabled);
    }

    @Operation(summary = "Disabilita un account | Disable an account", description = "Imposta enabled a false. | Sets enabled to false.")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Account disabilitato | Account disabled"), @ApiResponse(responseCode = "404", description = "Account non trovato | Account not found")})
    @Parameter(name = "id", description = "ID dell'account da disabilitare | ID of the account to disable")
    @PutMapping("/{id}/disable")
    public ResponseEntity<AccountResponseDTO> disableAccount(@PathVariable("id") Long id) {
        AccountResponseDTO disabled = service.disableAccount(id);
        return ResponseEntity.ok(disabled);
    }

    @Operation(summary = "Recupera account per persona | Get accounts by person", description = "Restituisce una lista paginata di account associati a una persona. | Returns paginated accounts linked to a specific person.")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Account trovati | Accounts found")})
    @Parameter(name = "personaId", description = "ID della persona | Person ID")
    @Parameter(name = "page", description = "Numero pagina | Page number")
    @Parameter(name = "size", description = "Numero elementi per pagina | Page size")
    @Parameter(name = "sort", description = "Criterio di ordinamento | Sort criteria")
    @GetMapping("/by-persona/{personaId}")
    public ResponseEntity<Page<AccountResponseDTO>> getAccountsByPersona(@PathVariable("personaId") Long personaId, Pageable pageable) {
        Page<AccountResponseDTO> page = service.getAccountsByPersona(personaId, pageable);
        return ResponseEntity.ok(page);
    }

    @Operation(summary = "Recupera account per ruolo | Get accounts by role", description = "Restituisce una lista paginata di account associati a un ruolo. | Returns paginated accounts linked to a specific role.")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Account trovati | Accounts found")})
    @Parameter(name = "roleId", description = "ID del ruolo | Role ID")
    @Parameter(name = "page", description = "Numero pagina | Page number")
    @Parameter(name = "size", description = "Numero elementi per pagina | Page size")
    @Parameter(name = "sort", description = "Criterio di ordinamento | Sort criteria")
    @GetMapping("/by-role/{roleId}")
    public ResponseEntity<Page<AccountResponseDTO>> getAccountsByRole(@PathVariable("roleId") Long roleId, Pageable pageable) {
        Page<AccountResponseDTO> page = service.getAccountsByRole(roleId, pageable);
        return ResponseEntity.ok(page);
    }

    @Operation(summary = "Recupera account bloccati | Get locked accounts", description = "Restituisce una lista paginata di account attualmente bloccati. | Returns paginated list of currently locked accounts.")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Account bloccati trovati | Locked accounts found")})
    @Parameter(name = "page", description = "Numero pagina | Page number")
    @Parameter(name = "size", description = "Numero elementi per pagina | Page size")
    @Parameter(name = "sort", description = "Criterio di ordinamento | Sort criteria")
    @GetMapping("/locked")
    public ResponseEntity<Page<AccountResponseDTO>> getLockedAccounts(Pageable pageable) {
        Page<AccountResponseDTO> page = service.getLockedAccounts(pageable);
        return ResponseEntity.ok(page);
    }

    @Operation(summary = "Recupera account tramite username | Get account by username", description = "Restituisce l'account corrispondente allo username specificato. | Returns the account matching the given username.")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Account trovato | Account found"), @ApiResponse(responseCode = "404", description = "Account non trovato | Account not found")})
    @Parameter(name = "username", description = "Username dell'account da cercare | Username of the account to find", required = true)
    @GetMapping("/by-username")
    public ResponseEntity<AccountResponseDTO> getAccountByUsername(@RequestParam("username") String username) {
        AccountResponseDTO dto = service.getAccountByUsername(username);
        return ResponseEntity.ok(dto);
    }

}
