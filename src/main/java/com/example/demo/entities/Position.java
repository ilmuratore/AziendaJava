package com.example.demo.entities;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 * Rappresenta una posizione lavorativa.
 * Represents a job position.
 */
@Schema(name = "Position", description = "Entità che rappresenta una posizione di lavoro | Entity representing a job position")
@Entity
@Table(name = "positions")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Position {

    /**
     * Identificativo della posizione.
     * Position identifier.
     */
    @Schema(description = "ID della posizione | Position ID", example = "1", required = true)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Titolo della posizione.
     * Position title.
     */
    @Schema(description = "Titolo della posizione | Position title", example = "Senior Developer", required = true)
    @Column(length = 100, nullable = false)
    private String title;

    /**
     * Descrizione della posizione.
     * Position description.
     */
    @Schema(description = "Descrizione della posizione | Position description")
    private String description;

    /**
     * Grado salariale.
     * Salary grade.
     */
    @Schema(description = "Grado salariale | Salary grade", example = "G5")
    @Column(name = "salary_grade", length = 10)
    private String salaryGrade;

    /**
     * Persone che ricoprono questa posizione.
     * Persons holding this position.
     */
    @Schema(description = "Persone con questa posizione | Persons with this position")
    @OneToMany(mappedBy = "position")
    private Set<Persona> personas;

}
