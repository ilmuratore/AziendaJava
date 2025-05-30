package com.example.demo.entities;

import com.example.demo.enums.TaskStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "tasks")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 200, nullable = false)
    private String title;

    private String description;

    @ManyToOne
    @JoinColumn(name = "assigned_to_id")
    private Persona assignedTo;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    @Column(name = "due_date")
    private LocalDate dueDate;

    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    private TaskStatus status;
}
