package com.school.management.api.model.responseModel;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ClassSubjectResponse {

    @JsonProperty("class_subject_id")
    private String classSubjectId;

    @JsonProperty("school_id")
    private String schoolId;

    @JsonProperty("class_name")
    private String className;

    @JsonProperty("subject")
    private String subject;

    @JsonProperty("academic_year_name")
    private String academicYearName;

}
