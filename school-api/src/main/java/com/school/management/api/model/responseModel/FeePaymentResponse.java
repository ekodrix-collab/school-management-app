package com.school.management.api.model.responseModel;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class FeePaymentResponse {

    private String paymentId;

    private String studentFeeId;

    private String schoolId;

    private String studentId;

    private Double paidAmount;

    private LocalDate paymentDate;

    private String paymentMethod;

    private String transactionReference;

    private String remarks;

    private LocalDateTime createdAt;
}
