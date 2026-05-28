package com.school.management.api.model.responseModel;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class StudentFeeResponse {

    @JsonProperty("student_fee_id")
    private String studentFeeId;

    @JsonProperty("fee_structure_id")
    private String feeStructureId;

    @JsonProperty("school_id")
    private String schoolId;

    @JsonProperty("academic_year_id")
    private String academicYearId;

    @JsonProperty("student_id")
    private String studentId;

    @JsonProperty("total_amount")
    private Double totalAmount;

    @JsonProperty("discount_amount")
    private Double discountAmount;

    @JsonProperty("fine_amount")
    private Double fineAmount;

    @JsonProperty("paid_amount")
    private Double paidAmount;

    @JsonProperty("balance_amount")
    private Double balanceAmount;

    @JsonProperty("status")
    private String status;

    @JsonProperty("remarks")
    private String remarks;

    @JsonProperty("created_at")
    private LocalDateTime createdAt;

}