package com.school.management.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {

    @JsonProperty("token_type")
    private String tokenType;

    @JsonProperty("access_token")
    private String accessToken;

    @JsonProperty("user_id")
    private UUID userId;

    @JsonProperty("role")
    private String role;

    @JsonProperty("name")
    private String name;

    @JsonProperty("issued_at")
    private Date issuedAt;

    @JsonProperty("expiry_date")
    private Date expiryDate;

    @JsonProperty("expiry_time_ms")
    private long expiryTimeMs;

}