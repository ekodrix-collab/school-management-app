package com.school.management.api.model.requstModel;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDate;

@Data
public class FeeStructureRequest {

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

}