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

    /**
     * The opaque reset token the client must supply to /auth/reset-password.
     * In production this should be delivered via email / SMS instead of
     * being returned directly in the response body.
     */
    @JsonProperty(value = "reset_token")
    private String resetToken;

    @JsonProperty(value = "expires_in_minutes")
    private int expiresInMinutes;
}
