package com.school.management.api.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "students")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "student_id")
    private String studentId;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "admission_number",nullable = false)
    private String admissionNumber;

    @Column(name = "roll_number", nullable = false)
    private String rollNumber;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Column(name ="gender",nullable = false)
    private String gender;

    @Column(name = "blood_group",length = 5)
    private String bloodGroup;

    @Column(name = "academic_year")
    private String academicYear;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;


}
