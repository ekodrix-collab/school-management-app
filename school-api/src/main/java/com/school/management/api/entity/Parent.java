package com.school.management.api.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@Table(name = "parents")
public class Parent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "parent_id")
    private UUID parentId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "spouse_name")
    private String spouseName;

    @Column(name = "mobile", nullable = false)
    private String mobile;

    @Column(name = "alternate_mobile")
    private String alternateMobile;

    @Column(name = "role")
    private String role;

    @Column(name = "password")
    private String password;

    @Column(name = "is_first_login")
    private Boolean isFirstLogin;

    @Column(name = "email")
    private String email;

    @Column(name = "house_name")
    private String houseName;

    @Column(name = "place")
    private String place;

    @Column(name = "city")
    private String city;

    @Column(name = "district")
    private String district;

    @Column(name = "state")
    private String state;

    @Column(name = "pincode")
    private String pincode;

    @Column(name = "landmark")
    private String landmark;

    @Column(name = "is_active")
    private Boolean isActive = true;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

}