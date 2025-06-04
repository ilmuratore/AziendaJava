package com.example.demo.dto.response;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

/**
 * DTO di riepilogo utilizzato per operazioni che coinvolgono lock/unlock di un account.
 * Summary DTO used for operations involving locking/unlocking an account.
 */
@Getter
@Setter
public class AccountSummaryDTO {

    /**
     * ID dell’account da bloccare/sbloccare.
     * ID of the account to lock/unlock.
     */
    @NotNull(message = "accountId è obbligatorio / accountId is required")
    private Long accountId;

    /**
     * Timestamp in cui l’account sarà sbloccato.
     * Deve essere nel futuro.
     *
     * Timestamp when the account will be unlocked.
     * Must be in the future.
     */
    @Future(message = "lockUntil deve essere nel futuro / lockUntil must be in the future")
    private Instant lockUntil;

    /**
     * Motivo opzionale del blocco dell’account.
     * Optional reason for locking the account.
     */
    private String reason;
}
