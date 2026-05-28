package com.school.management.api.model.requstModel;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ForgotPasswordRequest {

    @JsonProperty(value = "mobile")
    private String mobile;
}
