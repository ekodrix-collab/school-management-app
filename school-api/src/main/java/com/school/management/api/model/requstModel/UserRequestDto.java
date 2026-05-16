package com.school.management.api.model.requstModel;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class UserRequestDto {

    @JsonProperty(value = "school_id")
    private String schoolId;

    @JsonProperty(value = "name")
    private String name;

    @JsonProperty(value = "email")
    private String email;

    @JsonProperty(value = "mobile")
    private String mobile;

    @JsonProperty(value = "role")
    private String role;

}
