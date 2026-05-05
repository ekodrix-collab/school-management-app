package com.school.management.api.model.responseModel;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class SubjectResponseDto {

    private Long id;

    @JsonProperty("subject_id")
    private String subjectId;

    private String name;

    @JsonProperty("class_id")
    private String classId;

}
