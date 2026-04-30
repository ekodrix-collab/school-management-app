package com.school.management.api.model.responseModel;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SchoolClassResponse {
    @JsonProperty(value = "id")
    private Long id;
    @JsonProperty(value = "display_name")
    private String displayName;

    @JsonProperty(value = "capacity")
    private Integer capacity;

    @JsonProperty(value = "class_teacher_id")
    private String classTeacherId;

    @JsonProperty(value = "academic_year_id")
    private String academicYearID; // "2025-2026"

    @JsonProperty(value = "is_active")
    private Boolean isActive = true;

    @JsonProperty(value = "created_at")
    private LocalDateTime createdAt;

    @JsonProperty(value = "updated_at")
    private LocalDateTime updatedAt;
}
