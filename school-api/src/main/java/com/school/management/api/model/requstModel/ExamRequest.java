package com.school.management.api.model.requstModel;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ExamRequest {

    @JsonProperty("academic_year_id")
    private String academicYearId;

    @JsonProperty("exam_name")
    private String examName;

    @JsonProperty("exam_type")
    private String examType;

    @JsonProperty("start_date")
    private LocalDate startDate;

    @JsonProperty("end_date")
    private LocalDate endDate;

    @JsonProperty("result_publish_date")
    private LocalDate resultPublishDate;

    @JsonProperty("remarks")
    private String remarks;

}