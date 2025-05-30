package com.example.demo.dto;

import com.example.demo.enums.ProjectStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
public class ProjectDTO{

    private Long id;
    private String code;
    private String name;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private ProjectStatus status;
    private Instant createdAt;
    private Set<Long> teamMemberIds;
    private Set<Long> taskIds;
}