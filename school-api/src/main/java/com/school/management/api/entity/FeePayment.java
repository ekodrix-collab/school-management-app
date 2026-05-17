package com.school.management.api.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "fee_payments")
public class FeePayment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "payment_id", unique = true, nullable = false)
    private String paymentId;

    @Column(name = "student_fee_id", nullable = false)
    private String studentFeeId;

    @Column(name = "school_id", nullable = false)
    private String schoolId;

    @Column(name = "student_id", nullable = false)
    private String studentId;

    @Column(name = "paid_amount", nullable = false)
    private Double paidAmount;

    @Column(name = "payment_date")
    private LocalDate paymentDate;

    @Column(name = "payment_method")
    private String paymentMethod;
    // CASH
    // UPI
    // CARD
    // BANK_TRANSFER
    // CHEQUE

    @Column(name = "transaction_reference")
    private String transactionReference;

    @Column(name = "remarks")
    private String remarks;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

}