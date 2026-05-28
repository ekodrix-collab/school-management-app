package com.school.management.api.model.requstModel;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class StudentFeeRequest {

    @JsonProperty("fee_structure_id")
    private String feeStructureId;

    @JsonProperty("student_id")
    private String studentId;

    @JsonProperty("discount_amount")
    private Double discountAmount;

    @JsonProperty("fine_amount")
    private Double fineAmount;

    @JsonProperty("remarks")
    private String remarks;

}