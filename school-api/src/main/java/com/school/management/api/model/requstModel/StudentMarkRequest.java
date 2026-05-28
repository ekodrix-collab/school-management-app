package com.school.management.api.model.requstModel;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class StudentMarkRequest {

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

    @JsonProperty("attendance_status")
    private String attendanceStatus;

    @JsonProperty("remarks")
    private String remarks;

}