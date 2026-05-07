package com.school.management.api.model.responseModel;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class TeacherClassSubjectsResponse {

    @JsonProperty("teacher_class_subject_id")
    private String teacherClassSubjectId;

    @JsonProperty("teacher_name")
    private String teacherName;

    @JsonProperty("class_name")
    private String className;

    @JsonProperty("academic_year_name")
    private String academicYearName;

    @JsonProperty("subject_name")
    private String subjectName;

}
