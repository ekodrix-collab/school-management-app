package com.school.management.api.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "class_subjects", uniqueConstraints = {@UniqueConstraint(columnNames = {"school_id", "academic_year_id", "class_id", "subject_id"})})
public class ClassSubject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "class_subject_id")
    private String classSubjectId;

    @Column(name = "school_id")
    private String schoolId;

    @Column(name = "class_id", nullable = false)
    private String classId;

    @Column(name = "subject_id", nullable = false)
    private String subjectId;

    @Column(name = "academic_year_id")
    private String academicYearId;
    
}
