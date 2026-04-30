package com.school.management.api.model.requstModel;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class OnboardRequest {

    @JsonProperty(value = "name")
    private String name;

    @JsonProperty(value = "email")
    private String email;

    @JsonProperty(value = "mobile")
    private String mobile;

    @JsonProperty(value = "class_ids")
    private List<String> classIds;

    @JsonProperty(value = "role")
    private String role;

    @JsonProperty(value = "subject")
    private String subject;


}
