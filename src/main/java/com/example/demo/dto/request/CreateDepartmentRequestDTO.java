package com.example.demo.dto.request;

import com.example.demo.config.validation.ExistingPersona;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(name = "CreateDepartmentRequest", description = "Payload per creare un nuovo dipartimento | Payload to create a new department")
public class CreateDepartmentRequestDTO {

    @Schema(description = "Nome del dipartimento (unico) | Department name (unique)", example = "Risorse Umane", required = true)
    @NotBlank(message = "Il nome del dipartimento non può essere vuoto")
    @Size(max = 100, message = "Il nome non può superare i 100 caratteri")
    private String name;

    @Schema(description = "Sede del dipartimento | Department location", example = "Milano", required = false)
    @Size(max = 100, message = "La location non può superare i 100 caratteri")
    private String location;

    @Schema(description = "ID del manager del dipartimento | Department manager ID", example = "5", required = false)
    @ExistingPersona
    private Long managerId;


}
