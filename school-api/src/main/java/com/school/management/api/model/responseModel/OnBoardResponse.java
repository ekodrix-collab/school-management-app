package com.school.management.api.model.responseModel;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class OnBoardResponse {

    @JsonProperty(value = "success")
    private Boolean success;

    @JsonProperty(value = "message")
    private String message;

    @JsonProperty("name")
    private String name;

    @JsonProperty(value = "role")
    private String role;

    @JsonProperty(value = "is_first_login")
    private Boolean isFirstLogin;

    @JsonProperty(value = "subject")
    private List<String> subject;

    @JsonProperty(value = "classes")
    private List<String> classes;

}
