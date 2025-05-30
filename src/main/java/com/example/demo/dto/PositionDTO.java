package com.example.demo.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class PositionDTO{
    private Long id;
    private String title;
    private String description;
    private String salaryGrade;
    private Set<Long> personaIds;

 }