package com.school.management.api.model.responseModel;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;


@Data
public class SchoolClassResponse {

    @JsonProperty(value = "display_name")
    private String displayName;

    @JsonProperty(value = "academic_year_id")
    private String academicYearID;

    @JsonProperty(value = "is_active")
    private Boolean isActive = true;

    @JsonProperty(value = "division")
    private String division;

    @JsonProperty(value = "standard")
    private String standard;

    @JsonProperty(value = "class_id")
    private String classId;

}
