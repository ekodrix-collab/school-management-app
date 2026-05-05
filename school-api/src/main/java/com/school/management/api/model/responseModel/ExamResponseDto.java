package com.school.management.api.model.responseModel;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ExamResponseDto {

    private Long id;

    @JsonProperty("exam_id")
    private String examId;

    private String name;

    private String type;

    @JsonProperty("academic_year_id")
    private String academicYearId;

}
