package com.school.management.api.model.requstModel;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ForgotPasswordEmailRequest {

    @JsonProperty(value = "email")
    private String email;
}
