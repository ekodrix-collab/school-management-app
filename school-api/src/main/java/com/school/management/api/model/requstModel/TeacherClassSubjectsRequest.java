package com.school.management.api.model.requstModel;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.UUID;

@Data
public class TeacherClassSubjectsRequest {

    @JsonProperty(value = "teacher_id")
    private UUID teacherId;

    @JsonProperty(value = "class_subject_id")
    private String classSubjectId;

}
