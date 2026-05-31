package com.school.management.api.model.responseModel;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ForgotPasswordResponse {

    @JsonProperty(value = "message")
    private String message;

    @JsonProperty(value = "reset_token")
    private String resetToken;

    @JsonProperty(value = "expires_in_minutes")
    private int expiresInMinutes;
}
