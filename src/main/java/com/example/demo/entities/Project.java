package com.example.demo.entities;

import com.example.demo.entities.enums.ProjectStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Set;

/**
 * Rappresenta un progetto aziendale.
 * Represents a company project.
 */
@Schema(name = "Project", description = "Entità che rappresenta un progetto | Entity representing a project")
@Entity
@Table(name = "projects")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Project {

    /**
     * Identificativo del progetto.
     * Project identifier.
     */
    @Schema(description = "ID del progetto | Project ID", example = "1", required = true)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Codice univoco del progetto.
     * Unique project code.
     */
    @Schema(description = "Codice del progetto | Project code", example = "PRJ2025", required = true)
    @Column(length = 20, unique = true, nullable = false)
    private String code;

    /**
     * Nome del progetto.
     * Project name.
     */
    @Schema(description = "Nome del progetto | Project name", example = "Implementazione CRM", required = true)
    @Column(length = 200, nullable = false)
    private String name;

    /**
     * Descrizione del progetto.
     * Project description.
     */
    @Schema(description = "Descrizione del progetto | Project description")
    private String description;

    /**
     * Data di inizio.
     * Start date.
     */
    @Schema(description = "Data di inizio | Start date", example = "2025-06-01")
    @Column(name = "start_date")
    private LocalDate startDate;

    /**
     * Data di fine.
     * End date.
     */
    @Schema(description = "Data di fine | End date", example = "2025-12-31")
    @Column(name = "end_date")
    private LocalDate endDate;

    /**
     * Stato del progetto (enum).
     * Project status (enum).
     */
    @Schema(description = "Stato del progetto | Project status")
    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private ProjectStatus status;

    /**
     * Timestamp di creazione (readonly).
     * Creation timestamp (readonly).
     */
    @Schema(description = "Timestamp di creazione | Creation timestamp", accessMode = Schema.AccessMode.READ_ONLY)
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private Instant createdAt;

    /**
     * Persone appartenenti al team di progetto.
     * Persons belonging to the project team.
     */
    @Schema(description = "Team di progetto | Project team members")
    @ManyToMany(mappedBy = "projects")
    private Set<Persona> team;

    /**
     * Task associati al progetto.
     * Tasks associated with the project.
     */
    @Schema(description = "Task del progetto | Project tasks")
    @OneToMany(mappedBy = "project")
    private Set<Task> tasks;
}
