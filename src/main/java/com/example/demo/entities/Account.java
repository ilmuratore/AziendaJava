package com.example.demo.entities;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.Set;

/**
 * Rappresenta un account utente nel sistema.
 * Represents a user account in the system.
 */
@Schema(name = "Account", description = "Entità che rappresenta un account utente | Entity representing a user account")
@Entity
@Table(name = "accounts")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Account {


    /**
     * Identificativo univoco dell'account.
     * Unique identifier of the account.
     */
    @Schema(description = "Identificativo univoco dell'account | Unique account identifier", example = "1", required = true)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Username scelto dall'utente (unico e obbligatorio).
     * Username chosen by the user (unique, required).
     */
    @Schema(description = "Username scelto dall'utente (unico) | Username chosen by the user (unique)", example = "mario.rossi", required = true)
    @Column(length = 50, nullable = false, unique = true)
    private String username;

    /**
     * Hash della password (obbligatorio).
     * Password hash (required).
     */
    @Schema(description = "Hash della password | Password hash", example = "$2a$10$7QJ...", required = true)
    @Column(name = "password_hash", length = 255, nullable = false)
    private String passwordHash;

    /**
     * Flag che indica se l'email è stata verificata.
     * Flag indicating if the email has been verified.
     */
    @Schema(description = "Indica se l'email è stata verificata | Indicates if email is verified", example = "false")
    @Column(name = "email_verified")
    private Boolean emailVerified = false;

    /**
     * Flag che indica se l'account è abilitato.
     * Flag indicating if the account is enabled.
     */
    @Schema(description = "Indica se l'account è abilitato | Indicates if account is enabled", example = "true")
    private Boolean enabled = true;

    /**
     * Istante dell'ultimo login effettuato.
     * Timestamp of the last login.
     */
    @Schema(description = "Istante dell'ultimo login | Last login timestamp", example = "2025-05-30T12:34:56Z")
    @Column(name = "last_login")
    private Instant lastLogin;

    /**
     * Numero di tentativi di login falliti consecutivi.
     * Number of consecutive failed login attempts.
     */
    @Schema(description = "Numero di tentativi di login falliti consecutivi | Consecutive failed login attempts", example = "0")
    @Column(name = "failed_attempts")
    private Integer failedAttempts;

    /**
     * Istante fino al quale l'account rimane bloccato.
     * Timestamp until which the account is locked.
     */
    @Schema(description = "Timestamp di fine blocco dell'account | Account lock expiration timestamp", example = "2025-06-05T00:00:00Z")
    @Column(name = "locked_until")
    private Instant lockedUntil;

    /**
     * Istante di creazione dell'account (readonly).
     * Account creation timestamp (readonly).
     */
    @Schema(description = "Timestamp di creazione dell'account | Account creation timestamp", accessMode = Schema.AccessMode.READ_ONLY)
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private Instant createdAt;

    /**
     * Istante dell'ultimo aggiornamento (readonly).
     * Last update timestamp (readonly).
     */
    @Schema(description = "Istante dell'ultimo aggiornamento | Last update timestamp", accessMode = Schema.AccessMode.READ_ONLY)
    @UpdateTimestamp
    @Column(name = "updated_at")
    private Instant updatedAt;

    /**
     * Dati della persona associata all'account.
     * Data of the person associated with the account.
     */
    @Schema(description = "Persona associata all'account | Person associated with the account", required = true)
    @OneToOne
    @JoinColumn(name = "persona_id", nullable = false)
    private Persona persona;

    /**
     * Elenco dei ruoli assegnati all'account.
     * List of roles assigned to the account.
     */
    @Schema(description = "Ruoli assegnati all'account | Roles assigned to the account")
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "account_role",
            joinColumns = @JoinColumn(name = "account_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles;
}
