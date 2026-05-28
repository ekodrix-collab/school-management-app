package com.school.management.api.model.requstModel;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class ExamSubjectRequest {

    @JsonProperty("academic_year_id")
    private String academicYearId;

    @JsonProperty("exam_id")
    private String examId;

    @JsonProperty("class_id")
    private String classId;

    @JsonProperty("class_subject_id")
    private String classSubjectId;

    @JsonProperty("exam_date")
    private LocalDate examDate;

    @JsonProperty("start_time")
    private LocalTime startTime;

    @JsonProperty("end_time")
    private LocalTime endTime;

    @JsonProperty("max_mark")
    private Integer maxMark;

    @JsonProperty("pass_mark")
    private Integer passMark;

    @JsonProperty("remarks")
    private String remarks;

}