package com.school.management.api.model.responseModel;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class ExamResponse {

    @JsonProperty("exam_id")
    private String examId;

    @JsonProperty("school_id")
    private String schoolId;

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

    @JsonProperty("status")
    private String status;

    @JsonProperty("remarks")
    private String remarks;

    @JsonProperty("is_active")
    private Boolean isActive;

    @JsonProperty("created_at")
    private LocalDateTime createdAt;

}