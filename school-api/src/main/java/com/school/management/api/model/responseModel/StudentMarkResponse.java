package com.school.management.api.model.responseModel;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class StudentMarkResponse {

    @JsonProperty("student_mark_id")
    private String studentMarkId;

    @JsonProperty("school_id")
    private String schoolId;

    @JsonProperty("academic_year_id")
    private String academicYearId;

    @JsonProperty("exam_id")
    private String examId;

    @JsonProperty("exam_subject_id")
    private String examSubjectId;

    @JsonProperty("class_id")
    private String classId;

    @JsonProperty("student_id")
    private String studentId;

    @JsonProperty("obtained_mark")
    private Double obtainedMark;

    @JsonProperty("percentage")
    private Double percentage;

    @JsonProperty("grade")
    private String grade;

    @JsonProperty("result_status")
    private String resultStatus;

    @JsonProperty("attendance_status")
    private String attendanceStatus;

    @JsonProperty("remarks")
    private String remarks;

    @JsonProperty("is_published")
    private Boolean isPublished;

    @JsonProperty("created_at")
    private LocalDateTime createdAt;

}