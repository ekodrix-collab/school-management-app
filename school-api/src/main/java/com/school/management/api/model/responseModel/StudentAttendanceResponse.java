package com.school.management.api.model.responseModel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudentAttendanceResponse {

    private String studentAttendanceId;
    private String studentId;
    private String status;
    private String remarks;

}
