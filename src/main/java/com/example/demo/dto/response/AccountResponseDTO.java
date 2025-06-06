package com.example.demo.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.Set;


@Getter
@Setter
public class AccountResponseDTO {

    private Long id;

    private String username;

    private Boolean emailVerified;

    private Boolean enabled;

    private Instant lastLogin;

    private Integer failedAttempts;

    private Instant lockedUntil;

    private Instant createdAt;

    private Instant updatedAt;

    private Long personaId;

    private Set<Long> roleIds;

    private String personaFirstName;

    private String personaLastName;

    private Set<String> roleNames;
}
