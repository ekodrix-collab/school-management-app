package com.school.management.entity;

import jakarta.persistence.*;
import lombok.*;

import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Entity
@Table(name = "admissions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Admission extends BaseTenantEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String studentName;

    @Column(nullable = false)
    private LocalDate dateOfBirth;

    @Column(nullable = false)
    private String gender;

    @Column(nullable = false)
    private String parentName;

    @Column(nullable = false)
    private String contactNumber;

    private String email;

    @Column(nullable = false)
    private String appliedClass;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AdmissionStatus status = AdmissionStatus.PENDING;

    public enum AdmissionStatus {
        PENDING,
        APPROVED,
        REJECTED,
        COMPLETED
    }
}
