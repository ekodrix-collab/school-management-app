package com.school.management.api.model.requstModel;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ClassSubjectRequest {

    @JsonProperty("subject_id")
    private String subjectId;

    @JsonProperty("class_id")
    private String classId;

    @JsonProperty("academic_year_id")
    private String academicYearId;

}
