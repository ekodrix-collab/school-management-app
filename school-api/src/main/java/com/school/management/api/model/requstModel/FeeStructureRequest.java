package com.school.management.api.model.requstModel;

import lombok.Data;

import java.time.LocalDate;

@Data
public class FeeStructureRequest {

    private String academicYearId;

    private String classId;

    private String feeName;

    private String feeType;

    private Double amount;

    private LocalDate dueDate;

    private String description;

}
