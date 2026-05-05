package com.school.management.api.model.requstModel;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ExamRequestDto {

    @JsonProperty("exam_id")
    private String examId;

    private String name;

    private String type; // INTERNAL / FINAL

    @JsonProperty("academic_year_id")
    private String academicYearId;

}
