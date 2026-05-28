package com.school.management.api.model.responseModel;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudentAttendanceResponse {

    @JsonProperty("student_attendance_id")
    private String studentAttendanceId;

    @JsonProperty("student_id")
    private String studentId;

    @JsonProperty("status")
    private String status;

    @JsonProperty("remarks")
    private String remarks;

}