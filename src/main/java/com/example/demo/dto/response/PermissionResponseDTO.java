package com.example.demo.dto.response;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Schema(name = "PermissionResponse", description = "DTO per risposte sui permessi | DTO for permission responses")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PermissionResponseDTO {

    @Schema(description = "ID del permesso | Permission ID", example = "1")
    private Long id;

    @Schema(description = "Nome del permesso | Permission name", example = "USER_READ")
    private String name;

    @Schema(description = "Descrizione del permesso | Permission description", example = "Permette di leggere gli utenti")
    private String description;

    // Relazioni denormalizzate per il frontend
    @Schema(description = "ID dei ruoli che hanno questo permesso | IDs of roles that have this permission")
    private Set<Long> roleIds;

    @Schema(description = "Nomi dei ruoli che hanno questo permesso | Names of roles that have this permission")
    private Set<String> roleNames;

}
