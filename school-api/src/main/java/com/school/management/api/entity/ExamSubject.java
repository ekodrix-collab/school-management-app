package com.school.management.api.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Data
@Table(name = "exam_subjects", uniqueConstraints = {@UniqueConstraint(name = "uk_exam_class_subject", columnNames = {
                                "exam_id",
                                "class_id",
                                "class_subject_id"})})
public class ExamSubject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "exam_subject_id", unique = true, nullable = false)
    private String examSubjectId;

    @Column(name = "school_id", nullable = false)
    private String schoolId;

    @Column(name = "academic_year_id", nullable = false)
    private String academicYearId;

    @Column(name = "exam_id", nullable = false)
    private String examId;

    @Column(name = "class_id", nullable = false)
    private String classId;

    @Column(name = "class_subject_id", nullable = false)
    private String classSubjectId;

    @Column(name = "exam_date", nullable = false)
    private LocalDate examDate;

    @Column(name = "start_time")
    private LocalTime startTime;

    @Column(name = "end_time")
    private LocalTime endTime;

    @Column(name = "max_mark", nullable = false)
    private Integer maxMark;

    @Column(name = "pass_mark", nullable = false)
    private Integer passMark;

    @Column(name = "status")
    private String status;
    // SCHEDULED
    // COMPLETED
    // CANCELLED

    @Column(name = "remarks")
    private String remarks;

    @Column(name = "is_active")
    private Boolean isActive = true;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

}