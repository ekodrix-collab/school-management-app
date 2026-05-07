package com.school.management.api.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "school_classes", uniqueConstraints = {@UniqueConstraint(name = "uk_school_standard_division", columnNames = {"school_id", "standard", "division"})})
public class SchoolClass {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "class_id",unique = true)
    private String classId;

    @Column(name = "school_id")
    private String schoolId;

    @Column(name = "academic_year_id")
    private String academicYearId;

    @Column(name = "academic_name")
    private String academicName;

    @Column(name = "standard", nullable = false)
    private String standard;

    @Column(name = "division")
    private String division;

    @Column(name = "totalStudents")
    private Integer totalStudents;

    @Column(name = "is_active")
    private Boolean isActive = true;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

}
