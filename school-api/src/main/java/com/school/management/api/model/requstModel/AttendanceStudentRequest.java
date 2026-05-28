package com.school.management.api.model.requstModel;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AttendanceStudentRequest {

    @JsonProperty("student_id")
    private String studentId;

    @JsonProperty("status")
    private String status;

    @JsonProperty("remarks")
    private String remarks;
}