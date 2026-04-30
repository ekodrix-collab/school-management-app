package com.school.management.api.model.responseModel;

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

    @JsonProperty(value = "token_type")
    private String tokenType;

    @JsonProperty(value = "access_token")
    private String accessToken;

    @JsonProperty(value = "user_id")
    private UUID userId;

    @JsonProperty(value = "role")
    private String role;

    @JsonProperty(value = "name")
    private String name;

    @JsonProperty(value = "issued_at")
    private Date issuedAt;

    @JsonProperty(value = "expiry_date")
    private Date expiryDate;

    @JsonProperty(value = "expiry_time_ms")
    private long expiryTimeMs;

}