package com.school.management.api.model.requstModel;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.time.LocalDate;

@Data
public class AttendanceRequestDto {

    @JsonProperty(value = "student_id")
    private String studentId;

    @JsonProperty(value = "class_id")
    private String classId;

    @JsonProperty(value = "date")
    private LocalDate date;

    @JsonProperty(value = "status")
    private String status; // PRESENT / ABSENT / LEAVE

}
