package com.school.management.api.model.requstModel;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class MarkRequestDto {

    @JsonProperty("student_id")
    private String studentId;

    @JsonProperty("class_id")
    private String classId;

    @JsonProperty("subject_id")
    private String subjectId;

    @JsonProperty("exam_id")
    private String examId;

    @JsonProperty("academic_year_id")
    private String academicYearId;

    @JsonProperty("marks")
    private Integer marks;

    @JsonProperty("max_marks")
    private Integer maxMarks;

    @JsonProperty("remarks")
    private String remarks;

}
