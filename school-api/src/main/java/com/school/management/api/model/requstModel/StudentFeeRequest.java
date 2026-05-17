package com.school.management.api.model.requstModel;

import lombok.Data;

@Data
public class StudentFeeRequest {

    private String feeStructureId;

    private String studentId;

    private Double discountAmount;

    private Double fineAmount;

    private String remarks;

}