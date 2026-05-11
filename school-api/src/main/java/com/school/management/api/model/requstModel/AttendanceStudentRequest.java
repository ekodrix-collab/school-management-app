package com.school.management.api.model.requstModel;

import lombok.Data;

@Data
public class AttendanceStudentRequest {

    private String studentId;

    private String status;

    private String remarks;
}