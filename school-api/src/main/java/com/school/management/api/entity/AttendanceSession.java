package com.school.management.api.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "attendance_sessions", uniqueConstraints = {@UniqueConstraint(columnNames = {
                                "school_id",
                                "academic_year_id",
                                "class_id",
                                "attendance_date",
                                "session_type",
                                "period_number"})})
@Data
public class AttendanceSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "attendance_session_id", unique = true, nullable = false)
    private String attendanceSessionId;

    @Column(name = "school_id", nullable = false)
    private String schoolId;

    @Column(name = "academic_year_id", nullable = false)
    private String academicYearId;

    @Column(name = "class_id", nullable = false)
    private String classId;

    // TIMETABLE RELATION
    @Column(name = "timetable_id")
    private String timetableId;

    // ACTUAL SUBJECT TAKEN
    @Column(name = "class_subject_id")
    private String classSubjectId;

    // ACTUAL TEACHER TAKEN
    @Column(name = "teacher_id")
    private UUID teacherId;

    @Column(name = "attendance_date", nullable = false)
    private LocalDate attendanceDate;

    @Column(name = "session_type", nullable = false)
    private String sessionType;
    // MORNING
    // AFTERNOON
    // PERIOD

    @Column(name = "period_number")
    private Integer periodNumber;

    // SUBSTITUTION SUPPORT

    @Column(name = "is_substitution")
    private Boolean isSubstitution = false;

    // ORIGINAL TIMETABLE SUBJECT
    @Column(name = "original_class_subject_id")
    private String originalClassSubjectId;

    // ORIGINAL TIMETABLE TEACHER
    @Column(name = "original_teacher_id")
    private UUID originalTeacherId;

    @Column(name = "remarks")
    private String remarks;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}