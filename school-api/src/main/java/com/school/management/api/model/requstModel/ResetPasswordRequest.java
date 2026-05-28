package com.school.management.api.model.requstModel;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ResetPasswordRequest {

    @JsonProperty(value = "reset_token")
    private String resetToken;

    @JsonProperty(value = "new_password")
    private String newPassword;

    @JsonProperty(value = "confirm_password")
    private String confirmPassword;
}
