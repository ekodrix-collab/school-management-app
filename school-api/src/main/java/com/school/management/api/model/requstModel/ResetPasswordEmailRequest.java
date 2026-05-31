package com.school.management.api.model.requstModel;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ResetPasswordEmailRequest {

    @JsonProperty(value = "email")
    private String email;

    @JsonProperty(value = "token")
    private String token;

    @JsonProperty(value = "new_password")
    private String newPassword;

    @JsonProperty(value = "confirm_password")
    private String confirmPassword;
}
