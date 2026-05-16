package com.school.management.api.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "exams", uniqueConstraints = {@UniqueConstraint(name = "uk_school_exam_name_academic", columnNames = {
                                "school_id",
                                "academic_year_id",
                                "exam_name"})})
public class Exam {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "exam_id", unique = true, nullable = false)
    private String examId;

    @Column(name = "school_id", nullable = false)
    private String schoolId;

    @Column(name = "academic_year_id", nullable = false)
    private String academicYearId;

    @Column(name = "exam_name", nullable = false)
    private String examName;

    @Column(name = "exam_type")
    private String examType;
    // UNIT_TEST
    // MID_TERM
    // FINAL
    // PRACTICAL

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @Column(name = "result_publish_date")
    private LocalDate resultPublishDate;

    @Column(name = "status")
    private String status;
    // DRAFT
    // ACTIVE
    // COMPLETED
    // RESULT_PUBLISHED

    @Column(name = "remarks")
    private String remarks;

    @Column(name = "is_active")
    private Boolean isActive = true;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

}