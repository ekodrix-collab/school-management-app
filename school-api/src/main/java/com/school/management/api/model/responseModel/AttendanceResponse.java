package com.school.management.api.model.responseModel;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AttendanceResponse {

    @JsonProperty("attendance_session_id")
    private String attendanceSessionId;

    @JsonProperty("message")
    private String message;
}