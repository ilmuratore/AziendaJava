package com.example.demo.entities;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.util.Set;

/**
 * Rappresenta un dipartimento aziendale.
 * Represents a company department.
 */
@Schema(name = "Department", description = "Entità che rappresenta un dipartimento | Entity representing a department")
@Entity
@Table(name = "departments")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Department {

    /**
     * Identificativo del dipartimento.
     * Department identifier.
     */
    @Schema(description = "ID del dipartimento | Department ID", example = "1", required = true)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Nome del dipartimento (unico).
     * Department name (unique).
     */
    @Schema(description = "Nome del dipartimento | Department name", example = "Risorse Umane", required = true)
    @Column(length = 100, unique = true, nullable = false)
    private String name;

    /**
     * Manager del dipartimento.
     * Department manager.
     */
    @Schema(description = "Manager del dipartimento | Department manager")
    @OneToOne
    @JoinColumn(name = "manager_id")
    private Persona manager;

    /**
     * Location del dipartimento.
     * Department location.
     */
    @Schema(description = "Sede del dipartimento | Department location", example = "Milano")
    @Column(length = 100)
    private String location;

    /**
     * Timestamp di creazione del dipartimento (readonly).
     * Creation timestamp of the department (readonly).
     */
    @Schema(description = "Timestamp di creazione | Creation timestamp", accessMode = Schema.AccessMode.READ_ONLY)
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private Instant createdAt;

    /**
     * Elenco delle persone appartenenti al dipartimento.
     * List of persons in the department.
     */
    @Schema(description = "Elenco delle persone nel dipartimento | List of persons in the department")
    @OneToMany(mappedBy = "department")
    private Set<Persona> personas;
}
