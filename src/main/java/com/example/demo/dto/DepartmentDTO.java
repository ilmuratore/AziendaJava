package com.example.demo.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.Set;

@Getter
@Setter
public class DepartmentDTO {

    private Long id;
    private String name;
    private Long managerId;
    private String location;
    private Instant createdAt;
    private Set<Long> personaIds;
}