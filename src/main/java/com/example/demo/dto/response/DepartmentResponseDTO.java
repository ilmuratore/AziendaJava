package com.example.demo.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;


@Getter
@Setter
@Schema(name = "DepartmentResponse", description = "DTO di risposta che rappresenta un dipartimento | Response DTO representing a department")
public class DepartmentResponseDTO {

    @Schema(description = "ID del dipartimento | Department ID", example = "1", required = true)
    private Long id;

    @Schema(description = "Nome del dipartimento | Department name", example = "Risorse Umane", required = true)
    private String name;

    @Schema(description = "Sede del dipartimento | Department location", example = "Milano")
    private String location;

    @Schema(description = "Timestamp di creazione del dipartimento | Creation timestamp",
            example = "2025-06-05T12:00:00Z", accessMode = Schema.AccessMode.READ_ONLY)
    private Instant createdAt;


    @Schema(description = "ID del manager | Manager ID", example = "3")
    private Long managerId;

    @Schema(description = "Nome del manager | Manager first name", example = "Mario", accessMode = Schema.AccessMode.READ_ONLY)
    private String managerFirstName;

    @Schema(description = "Cognome del manager | Manager last name", example = "Rossi", accessMode = Schema.AccessMode.READ_ONLY)
    private String managerLastName;

}
