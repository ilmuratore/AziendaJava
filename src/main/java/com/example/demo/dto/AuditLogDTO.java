package com.example.demo.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class AuditLogDTO{

    private Long id;
    private String entityName;
    private Long entityId;
    private String action;
    private Long changedByAccountId;
    private Instant timestamp;
    private String details;

}