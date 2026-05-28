package com.school.management.api.model.responseModel;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class FeePaymentResponse {

    @JsonProperty("payment_id")
    private String paymentId;

    @JsonProperty("student_fee_id")
    private String studentFeeId;

    @JsonProperty("school_id")
    private String schoolId;

    @JsonProperty("student_id")
    private String studentId;

    @JsonProperty("paid_amount")
    private Double paidAmount;

    @JsonProperty("payment_date")
    private LocalDate paymentDate;

    @JsonProperty("payment_method")
    private String paymentMethod;

    @JsonProperty("transaction_reference")
    private String transactionReference;

    @JsonProperty("remarks")
    private String remarks;

    @JsonProperty("created_at")
    private LocalDateTime createdAt;
}