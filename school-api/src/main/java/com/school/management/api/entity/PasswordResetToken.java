package com.school.management.api.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@Table(name = "password_reset_tokens")
public class PasswordResetToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email")
    private String email;

    @Column(name = "otp")
    private String otp;

    @Column(name = "token", nullable = false, unique = true)
    private String token;

    @Column(name = "expires_at", nullable = false)
    private LocalDateTime expiresAt;

    @Column(name = "is_used")
    private Boolean isUsed = false;

    @Column(name = "otp_verified")
    private Boolean otpVerified = false;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;


    public PasswordResetToken(String email, String otp, String token, LocalDateTime expiresAt) {
        this.email = email;
        this.otp = otp;
        this.token = token;
        this.expiresAt = expiresAt;
        this.isUsed = false;
        this.otpVerified = false;
        this.createdAt = LocalDateTime.now();
    }

    public boolean isExpired() {
        return LocalDateTime.now().isAfter(this.expiresAt);
    }
}
