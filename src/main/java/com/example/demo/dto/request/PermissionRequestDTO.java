package com.example.demo.dto.request;

import com.example.demo.config.validation.UniquePermissionName;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(name = "PermissionRequest", description = "DTO per richieste sui permessi | DTO for permission requests")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PermissionRequestDTO {

    @Schema(description = "Nome del permesso | Permission name", example = "USER_READ", required = true)
    @NotBlank(message = "Il nome del permesso è obbligatorio | Permission name is required")
    @Size(min = 2, max = 50, message = "Il nome deve essere tra 2 e 50 caratteri | Name must be between 2 and 50 characters")
    @UniquePermissionName
    private String name;

    @Schema(description = "Descrizione del permesso | Permission description", example = "Permette di leggere gli utenti")
    @Size(max = 255, message = "La descrizione non può superare i 255 caratteri | Description cannot exceed 255 characters")
    private String description;

}
