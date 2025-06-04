package com.example.demo.entities;

import com.example.demo.entities.enums.TaskStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * Rappresenta un task.
 * Represents a task.
 */
@Schema(name = "Task", description = "Entità che rappresenta un task | Entity representing a task")
@Entity
@Table(name = "tasks")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Task {

    /**
     * Identificativo del task.
     * Task identifier.
     */
    @Schema(description = "ID del task | Task ID", example = "1", required = true)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Titolo del task.
     * Task title.
     */
    @Schema(description = "Titolo del task | Task title", example = "Analisi requisiti", required = true)
    @Column(length = 200, nullable = false)
    private String title;

    /**
     * Descrizione del task.
     * Task description.
     */
    @Schema(description = "Descrizione del task | Task description")
    private String description;

    /**
     * Persona a cui è assegnato.
     * Person assigned to.
     */
    @Schema(description = "Assegnato a | Assigned to")
    @ManyToOne
    @JoinColumn(name = "assigned_to_id")
    private Persona assignedTo;

    /**
     * Progetto di appartenenza.
     * Parent project.
     */
    @Schema(description = "Progetto | Project")
    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    /**
     * Data di scadenza.
     * Due date.
     */
    @Schema(description = "Data di scadenza | Due date", example = "2025-07-15")
    @Column(name = "due_date")
    private LocalDate dueDate;

    /**
     * Stato del task (enum).
     * Task status (enum).
     */
    @Schema(description = "Stato del task | Task status")
    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    private TaskStatus status;
}
