package com.school.management.api.model.responseModel;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class FeeStructureResponse {

    @JsonProperty("fee_structure_id")
    private String feeStructureId;

    @JsonProperty("school_id")
    private String schoolId;

    @JsonProperty("academic_year_id")
    private String academicYearId;

    @JsonProperty("class_id")
    private String classId;

    @JsonProperty("fee_name")
    private String feeName;

    @JsonProperty("fee_type")
    private String feeType;

    @JsonProperty("amount")
    private Double amount;

    @JsonProperty("due_date")
    private LocalDate dueDate;

    @JsonProperty("description")
    private String description;

    @JsonProperty("is_active")
    private Boolean isActive;

    @JsonProperty("created_at")
    private LocalDateTime createdAt;

}