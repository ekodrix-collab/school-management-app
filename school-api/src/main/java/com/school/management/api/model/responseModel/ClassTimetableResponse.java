package com.school.management.api.model.responseModel;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClassTimetableResponse {

    @JsonProperty("timetable_id")
    private String timetableId;

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

    @JsonProperty("is_active")
    private Boolean isActive;

    @JsonProperty("message")
    private String message;
}