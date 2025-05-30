package com.example.demo.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.Set;

@Getter
@Setter
public class AccountDTO{

    private Long id;
    private String username;
    private String passwordHash;
    private Boolean emailVerified;
    private Boolean enabled;
    private Instant lastLogin;
    private Integer failedAttempts;
    private Instant lockedUntil;
    private Instant createdAt;
    private Instant updatedAt;
    private Long personaId;
    private Set<Long> roleIds;

}