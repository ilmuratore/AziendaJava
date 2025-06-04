package com.example.demo.dto;

import com.example.demo.entities.enums.TaskStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class TaskDTO{

    private Long id;
    private String title;
    private String description;
    private Long assignedToId;
    private Long projectId;
    private LocalDate dueDate;
    private TaskStatus status;
}