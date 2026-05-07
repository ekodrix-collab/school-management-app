package com.school.management.api.model.responseModel;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class SubjectResponseDto {

    @JsonProperty("subject_id")
    private String subjectId;

    @JsonProperty("name")
    private String name;

    @JsonProperty("school_id")
    private String schoolId;

}
