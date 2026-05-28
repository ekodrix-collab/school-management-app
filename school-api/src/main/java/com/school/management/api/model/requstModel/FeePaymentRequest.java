package com.school.management.api.model.requstModel;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class FeePaymentRequest {

    @JsonProperty("student_fee_id")
    private String studentFeeId;

    @JsonProperty("paid_amount")
    private Double paidAmount;

    @JsonProperty("payment_method")
    private String paymentMethod;

    @JsonProperty("transaction_reference")
    private String transactionReference;

    @JsonProperty("remarks")
    private String remarks;
}