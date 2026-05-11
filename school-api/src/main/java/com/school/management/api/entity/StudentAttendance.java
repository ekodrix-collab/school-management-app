package com.school.management.api.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "student_attendance", uniqueConstraints = {@UniqueConstraint(columnNames = {
                                "attendance_session_id",
                                "student_id"})})
@Data
public class StudentAttendance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "student_attendance_id", unique = true, nullable = false)
    private String studentAttendanceId;

    @Column(name = "attendance_session_id", nullable = false)
    private String attendanceSessionId;

    @Column(name = "student_id", nullable = false)
    private String studentId;

    @Column(name = "status", nullable = false)
    private String status;
    // PRESENT
    // ABSENT
    // LEAVE
    // HALF_DAY

    @Column(name = "remarks")
    private String remarks;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}