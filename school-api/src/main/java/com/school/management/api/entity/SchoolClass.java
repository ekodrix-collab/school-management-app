package com.school.management.api.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "school_classes")
public class SchoolClass {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "standard", nullable = false)
    private String standard;        // "1", "LKG"

    @Column(name = "division")
    private String division;      // "A", "B"

    @Column(name = "display_name")
    private String displayName;  // "1-A"

    @Column(name = "capacity")
    private Integer capacity;

    // Simple approach (MVP)
    @Column(name = "class_teacher_id")
    private String classTeacherId;

    @Column(name = "academic_year_id")
    private String academicYearID; // "2025-2026"

    @Column(name = "is_active")
    private Boolean isActive = true;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
