package com.example.demo.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(name = "UpdateDepartmentRequest", description = "Payload per aggiornare un dipartimento | Payload to update a department")
public class UpdateDepartmentRequestDTO {

    @Schema(description = "Nuovo nome del dipartimento | New department name", example = "HR & Talent", required = false)
    @Size(max = 100, message = "Il nome non può superare i 100 caratteri")
    private String name;

    @Schema(description = "Nuova sede del dipartimento | New department location", example = "Roma", required = false)
    @Size(max = 100, message = "La location non può superare i 100 caratteri")
    private String location;

    @Schema(description = "Nuovo ID del manager | New manager ID", example = "7", required = false)
    private Long managerId;
}
