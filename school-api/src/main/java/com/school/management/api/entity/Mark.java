package com.school.management.api.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "marks", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"student_id", "subject_id", "exam_id", "academic_year_id"})
})
public class Mark {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "student_id", nullable = false)
    private String studentId;

    @Column(name = "class_id", nullable = false)
    private String classId;

    @Column(name = "subject_id", nullable = false)
    private String subjectId;

    @Column(name = "exam_id", nullable = false)
    private String examId;

    @Column(name = "school_id")
    private String schoolId;

    @Column(name = "academic_year_id", nullable = false)
    private String academicYearId;

    @Column(name = "marks", nullable = false)
    private Integer marks;

    @Column(name = "max_marks", nullable = false)
    private Integer maxMarks;

    @Column(name = "grade", nullable = false)
    private String grade;

    @Column(name = "remarks")
    private String remarks;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

}
