package com.school.management.api.model.requstModel;

import lombok.Data;

@Data
public class FeePaymentRequest {

    private String studentFeeId;

    private Double paidAmount;

    private String paymentMethod;

    private String transactionReference;

    private String remarks;
}
