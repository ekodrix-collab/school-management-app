package com.school.management.api.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@Table(name = "students")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "student_id")
    private String studentId;

    @Column(name = "parent_id")
    private UUID parentId;

    @Column(name = "school_id")
    private String schoolId;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "admission_number",nullable = false)
    private Long admissionNumber;

    @Column(name = "adhar_no")
    private String adharNo;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Column(name ="gender",nullable = false)
    private String gender;

    @Column(name = "blood_group",length = 5)
    private String bloodGroup;

    @Column(name = "first_language")
    private String firstLanguage;

    @Column(name = "second_language")
    private String secondLanguage;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;


}
