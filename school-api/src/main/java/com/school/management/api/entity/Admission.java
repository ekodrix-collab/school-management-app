package com.school.management.api.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Table(name = "admission")
public class Admission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "admission_id", nullable = false)
    private String admissionId;

    @Column(name = "school_id", nullable = false)
    private String schoolId;

    @Column(name = "student_id", nullable = false)
    private String studentId;

    @Column(name = "class_id", nullable = false)
    private String classId;

    @Column(name = "academic_year_id", nullable = false)
    private String academicYearId;

    @Column(name = "roll_number", nullable = false)
    private Integer rollNumber;

    @Column(name = "status")
    private String status;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

}
