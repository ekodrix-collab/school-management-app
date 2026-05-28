package com.school.management.api.model.requstModel;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalTime;
import java.util.UUID;

@Data
public class ClassTimetableRequest {

    @JsonProperty("school_id")
    private String schoolId;

    @JsonProperty("academic_year_id")
    private String academicYearId;

    @JsonProperty("class_id")
    private String classId;

    @JsonProperty("class_subject_id")
    private String classSubjectId;

    @JsonProperty("teacher_id")
    private UUID teacherId;

    @JsonProperty("day_name")
    private String dayName;

    @JsonProperty("period_number")
    private Integer periodNumber;

    @JsonProperty("start_time")
    private LocalTime startTime;

    @JsonProperty("end_time")
    private LocalTime endTime;

    @JsonProperty("is_break")
    private Boolean isBreak;

}