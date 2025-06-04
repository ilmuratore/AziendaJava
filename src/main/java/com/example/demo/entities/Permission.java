package com.example.demo.entities;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Rappresenta un permesso assegnabile ai ruoli.
 * Represents a permission assignable to roles.
 */
@Schema(name = "Permission", description = "Entità che rappresenta un permesso | Entity representing a permission")
@Entity
@Table(name = "permissions")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Permission {

    /**
     * Identificativo del permesso.
     * Permission identifier.
     */
    @Schema(description = "ID del permesso | Permission ID", example = "1", required = true)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Nome univoco del permesso.
     * Unique name of the permission.
     */
    @Schema(description = "Nome del permesso | Permission name", example = "USER_READ", required = true)
    @Column(length = 50, unique = true, nullable = false)
    private String name;


    /**
     * Descrizione del permesso.
     * Permission description.
     */
    @Schema(description = "Descrizione del permesso | Permission description")
    private String description;
}
