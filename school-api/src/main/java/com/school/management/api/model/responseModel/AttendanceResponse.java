package com.school.management.api.model.responseModel;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AttendanceResponse {

    private String attendanceSessionId;

    private String message;
}