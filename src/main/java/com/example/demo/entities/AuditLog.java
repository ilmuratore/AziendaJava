package com.example.demo.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.Instant;

@Entity
@Table(name = "audit_Logs")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuditLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "entity_name", length = 100)
    private String entityName;

    @Column(name = "entity_id")
    private Long entityId;

    @Column(length = 50)
    private String action;

    @ManyToOne
    @JoinColumn(name = "changed_by_id")
    private Account changedBy;

    @CreatedDate
    @Column(name = "timestamp", updatable = false)
    private Instant timestamp;

    @Column(columnDefinition = "TEXT")
    private String details;
}
