package com.example.demo.entities;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.Set;

/**
 * Rappresenta un team.
 * Represents a team.
 */
@Schema(name = "Team", description = "Entità che rappresenta un team | Entity representing a team")
@Entity
@Table(name = "teams")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Team {

    /**
     * Identificativo del team.
     * Team identifier.
     */
    @Schema(description = "ID del team | Team ID", example = "1", required = true)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Nome del team.
     * Team name.
     */
    @Schema(description = "Nome del team | Team name", example = "Team Alpha", required = true)
    @Column(length = 100, nullable = false)
    private String name;

    /**
     * Descrizione del team.
     * Team description.
     */
    @Schema(description = "Descrizione del team | Team description")
    private String description;

    /**
     * Membri del team.
     * Team members.
     */
    @Schema(description = "Membri del team | Team members")
    @ManyToMany(mappedBy = "teams")
    private Set<Persona> members;

}
