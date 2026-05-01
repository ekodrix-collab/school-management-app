package com.school.management.entity;

import jakarta.persistence.*;
import lombok.*;

import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "schools")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class School extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String schoolCode;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String address;

    private String contactNumber;

    private String email;

    @Column(nullable = false)
    private boolean active = true;

    private String subscriptionType;
}
