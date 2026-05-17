package com.school.management.api.model.responseModel;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class StudentFeeResponse {

    private String studentFeeId;

    private String feeStructureId;

    private String schoolId;

    private String academicYearId;

    private String studentId;

    private Double totalAmount;

    private Double discountAmount;

    private Double fineAmount;

    private Double paidAmount;

    private Double balanceAmount;

    private String status;

    private String remarks;

    private LocalDateTime createdAt;

}
