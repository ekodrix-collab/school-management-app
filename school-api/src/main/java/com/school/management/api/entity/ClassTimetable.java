package com.school.management.api.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

@Entity
@Table(name = "class_timetables", uniqueConstraints = {@UniqueConstraint(columnNames = {
                                "school_id",
                                "academic_year_id",
                                "class_id",
                                "day_name",
                                "period_number"})})
@Data
public class ClassTimetable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "timetable_id", unique = true, nullable = false)
    private String timetableId;

    @Column(name = "school_id", nullable = false)
    private String schoolId;

    @Column(name = "academic_year_id", nullable = false)
    private String academicYearId;

    @Column(name = "class_id", nullable = false)
    private String classId;

    @Column(name = "class_subject_id", nullable = false)
    private String classSubjectId;

    @Column(name = "teacher_id", nullable = false)
    private UUID teacherId;

    @Column(name = "day_name", nullable = false)
    private String dayName;
    // MONDAY, TUESDAY...

    @Column(name = "period_number", nullable = false)
    private Integer periodNumber;

    @Column(name = "start_time")
    private LocalTime startTime;

    @Column(name = "end_time")
    private LocalTime endTime;

    @Column(name = "is_active")
    private Boolean isActive = true;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
