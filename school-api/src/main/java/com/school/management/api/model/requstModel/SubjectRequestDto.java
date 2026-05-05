package com.school.management.api.model.requstModel;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class SubjectRequestDto {

    @JsonProperty("subject_id")
    private String subjectId;

    private String name;

    @JsonProperty("class_id")
    private String classId;

}
