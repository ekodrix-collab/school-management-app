package com.school.management.api.model.responseModel;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class AttendanceResponseDto {

    @JsonProperty(value = "id")
    private Long id;

    @JsonProperty(value = "student_id")
    private String studentId;

    @JsonProperty(value = "class_id")
    private String classId;

    @JsonProperty(value = "date")
    private LocalDate date;

    @JsonProperty(value = "status")
    private String status;

    @JsonProperty(value = "created_at")
    private LocalDateTime createdAt;

    @JsonProperty(value = "updated_at")
    private LocalDateTime updatedAt;

}
