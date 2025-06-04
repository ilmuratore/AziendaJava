package com.example.demo.entities;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 * Rappresenta un ruolo utente.
 * Represents a user role.
 */
@Schema(name = "Role", description = "Entità che rappresenta un ruolo | Entity representing a role")
@Entity
@Table(name = "roles")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Role {

    /**
     * Identificativo del ruolo.
     * Role identifier.
     */
    @Schema(description = "ID del ruolo | Role ID", example = "1", required = true)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Nome univoco del ruolo.
     * Unique role name.
     */
    @Schema(description = "Nome del ruolo | Role name", example = "ADMIN", required = true)
    @Column(length = 50, unique = true, nullable = false)
    private String name;

    /**
     * Descrizione del ruolo.
     * Role description.
     */
    @Schema(description = "Descrizione del ruolo | Role description")
    private String description;

    /**
     * Permessi associati al ruolo.
     * Permissions associated with the role.
     */
    @Schema(description = "Permessi del ruolo | Role permissions")
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "role_permission",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "permission_id")
    )
    private Set<Permission> permissions;
}
