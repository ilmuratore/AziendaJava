package com.example.demo.entities;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.Instant;

/**
 * Registra le operazioni di audit sul database.
 * Records audit operations in the database.
 */
@Schema(name = "AuditLog", description = "Entità per i log di audit | Entity for audit logs")
@Entity
@Table(name = "audit_Logs")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuditLog {

    /**
     * Identificativo del log.
     * Unique identifier of the log.
     */
    @Schema(description = "Identificativo del log | Log identifier", example = "1", required = true)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Nome dell'entità modificata.
     * Name of the modified entity.
     */
    @Schema(description = "Nome dell'entità modificata | Modified entity name", example = "Account")
    @Column(name = "entity_name", length = 100)
    private String entityName;

    /**
     * ID dell'entità modificata.
     * ID of the modified entity.
     */
    @Schema(description = "ID dell'entità modificata | Modified entity ID", example = "1")
    @Column(name = "entity_id")
    private Long entityId;

    /**
     * Azione eseguita sull'entità.
     * Action performed on the entity.
     */
    @Schema(description = "Azione eseguita | Action performed", example = "UPDATE")
    @Column(length = 50)
    private String action;

    /**
     * Account che ha effettuato la modifica.
     * Account who performed the change.
     */
    @Schema(description = "Utente che ha effettuato la modifica | User who performed the change")
    @ManyToOne
    @JoinColumn(name = "changed_by_id")
    private Account changedBy;

    /**
     * Timestamp della registrazione del log (readonly).
     * Timestamp when the log was recorded (readonly).
     */
    @Schema(description = "Timestamp del log | Log timestamp", accessMode = Schema.AccessMode.READ_ONLY)
    @CreatedDate
    @Column(name = "timestamp", updatable = false)
    private Instant timestamp;

    /**
     * Dettagli della modifica.
     * Details of the change.
     */
    @Schema(description = "Dettagli della modifica | Change details", example = "{\"field\":\"username\",\"old\":\"oldUser\",\"new\":\"newUser\"}")
    @Column(columnDefinition = "TEXT")
    private String details;
}
