package com.school.management.api.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "student_marks", uniqueConstraints = {@UniqueConstraint(name = "uk_exam_subject_student", columnNames = {
                                "exam_subject_id",
                                "student_id"})})
public class StudentMark {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "student_mark_id", unique = true, nullable = false)
    private String studentMarkId;

    @Column(name = "school_id", nullable = false)
    private String schoolId;

    @Column(name = "academic_year_id", nullable = false)
    private String academicYearId;

    @Column(name = "exam_id", nullable = false)
    private String examId;

    @Column(name = "exam_subject_id", nullable = false)
    private String examSubjectId;

    @Column(name = "class_id", nullable = false)
    private String classId;

    @Column(name = "student_id", nullable = false)
    private String studentId;

    @Column(name = "obtained_mark", nullable = false)
    private Double obtainedMark;

    @Column(name = "percentage")
    private Double percentage;

    @Column(name = "grade")
    private String grade;

    @Column(name = "result_status")
    private String resultStatus;
    // PASS
    // FAIL

    @Column(name = "attendance_status")
    private String attendanceStatus;
    // PRESENT
    // ABSENT
    // LEAVE
    // MALPRACTICE

    @Column(name = "remarks")
    private String remarks;

    @Column(name = "is_published")
    private Boolean isPublished = false;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

}