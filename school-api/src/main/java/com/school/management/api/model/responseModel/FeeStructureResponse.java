package com.school.management.api.model.responseModel;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class FeeStructureResponse {

    private String feeStructureId;

    private String schoolId;

    private String academicYearId;

    private String classId;

    private String feeName;

    private String feeType;

    private Double amount;

    private LocalDate dueDate;

    private String description;

    private Boolean isActive;

    private LocalDateTime createdAt;

}