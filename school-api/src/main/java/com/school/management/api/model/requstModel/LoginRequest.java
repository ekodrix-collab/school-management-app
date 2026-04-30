package com.school.management.api.model.requstModel;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class LoginRequest {

    @JsonProperty(value = "mobile")
    private String mobile;

    @JsonProperty(value = "password")
    private String password;
}