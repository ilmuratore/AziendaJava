package com.example.demo.dto.response;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class AccountSummaryDTO {

    @NotNull(message = "accountId è obbligatorio / accountId is required")
    private Long accountId;

    @Future(message = "lockUntil deve essere nel futuro / lockUntil must be in the future")
    private Instant lockUntil;

    private String reason;
}
