package com.example.demo.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.Set;

/**
 * DTO di risposta contenente i dettagli completi di un account.
 * Response DTO containing full account details.
 */
@Getter
@Setter
public class AccountResponseDTO {

    /**
     * ID univoco dell’account.
     * Unique ID of the account.
     */
    private Long id;

    /**
     * Username dell’account.
     * Username of the account.
     */
    private String username;

    /**
     * Stato di verifica dell’email (true = verificata, false = non verificata).
     * Email verification status (true = verified, false = not verified).
     */
    private Boolean emailVerified;

    /**
     * Stato di abilitazione dell’account (true = abilitato, false = disabilitato).
     * Account enabled status (true = enabled, false = disabled).
     */
    private Boolean enabled;

    /**
     * Timestamp dell’ultimo accesso effettuato dall’account.
     * Timestamp of the account's last login.
     */
    private Instant lastLogin;

    /**
     * Numero di tentativi di accesso falliti consecutivi.
     * Number of consecutive failed login attempts.
     */
    private Integer failedAttempts;

    /**
     * Timestamp fino al quale l’account risulta bloccato (null se non bloccato).
     * Timestamp until which the account is locked (null if not locked).
     */
    private Instant lockedUntil;

    /**
     * Timestamp di creazione dell’account.
     * Timestamp of account creation.
     */
    private Instant createdAt;

    /**
     * Timestamp dell’ultimo aggiornamento dell’account.
     * Timestamp of last account update.
     */
    private Instant updatedAt;

    // Dati relazionali

    /**
     * ID della Persona associata all’account.
     * ID of the Persona associated with the account.
     */
    private Long personaId;

    /**
     * Insieme di ID dei Role associati all’account.
     * Set of Role IDs associated with the account.
     */
    private Set<Long> roleIds;

    // Dati denormalizzati per frontend

    /**
     * Nome della Persona (denormalizzato per comodità).
     * First name of the Persona (denormalized for convenience).
     */
    private String personaFirstName;

    /**
     * Cognome della Persona (denormalizzato per comodità).
     * Last name of the Persona (denormalized for convenience).
     */
    private String personaLastName;

    /**
     * Insieme di nomi dei ruoli (denormalizzato per comodità).
     * Set of role names (denormalized for convenience).
     */
    private Set<String> roleNames;
}
