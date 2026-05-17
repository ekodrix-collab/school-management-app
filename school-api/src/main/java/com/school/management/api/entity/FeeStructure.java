package com.school.management.api.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "fee_structures", uniqueConstraints = {@UniqueConstraint(name = "uk_fee_structure", columnNames = {
                                "school_id",
                                "academic_year_id",
                                "class_id",
                                "fee_name"})})
public class FeeStructure {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fee_structure_id", unique = true, nullable = false)
    private String feeStructureId;

    @Column(name = "school_id", nullable = false)
    private String schoolId;

    @Column(name = "academic_year_id", nullable = false)
    private String academicYearId;

    @Column(name = "class_id", nullable = false)
    private String classId;

    @Column(name = "fee_name", nullable = false)
    private String feeName;

    @Column(name = "fee_type")
    private String feeType;
    // TUITION
    // EXAM
    // TRANSPORT
    // HOSTEL

    @Column(name = "amount", nullable = false)
    private Double amount;

    @Column(name = "due_date")
    private LocalDate dueDate;

    @Column(name = "description")
    private String description;

    @Column(name = "is_active")
    private Boolean isActive = true;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

}