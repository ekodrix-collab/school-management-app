package com.school.management.api.model.responseModel;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class SchoolResponse {

    @JsonProperty(value = "school_name")
    private String schoolName;

    @JsonProperty(value = "school_code")
    private String schoolCode;

    @JsonProperty(value = "admin")
    private UserResponse admin;
}
