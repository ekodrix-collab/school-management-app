package com.school.management.api.model.responseModel;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class ParentResponse {

    @JsonProperty(value = "name")
    private String name;

    @JsonProperty(value = "email")
    private String email;

    @JsonProperty(value = "role")
    private String role;

    @JsonProperty("students")
    private List<String> students;

    @JsonProperty(value = "is_active")
    private Boolean isActive;

}
