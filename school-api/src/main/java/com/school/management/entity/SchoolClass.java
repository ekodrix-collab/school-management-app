package com.school.management.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "classes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class SchoolClass extends BaseTenantEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String className; // e.g., 10th

    @Column(nullable = false)
    private String division; // e.g., A, B

    @OneToMany(mappedBy = "schoolClass", cascade = CascadeType.ALL)
    private List<Student> students;
}
