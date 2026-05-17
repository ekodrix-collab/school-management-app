package com.school.management.api.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "student_fees", uniqueConstraints = {@UniqueConstraint(name = "uk_student_fee", columnNames = {
                                "fee_structure_id",
                                "student_id"})})
public class StudentFee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "student_fee_id", unique = true, nullable = false)
    private String studentFeeId;

    @Column(name = "fee_structure_id", nullable = false)
    private String feeStructureId;

    @Column(name = "school_id", nullable = false)
    private String schoolId;

    @Column(name = "academic_year_id", nullable = false)
    private String academicYearId;

    @Column(name = "student_id", nullable = false)
    private String studentId;

    @Column(name = "total_amount", nullable = false)
    private Double totalAmount;

    @Column(name = "discount_amount")
    private Double discountAmount = 0.0;

    @Column(name = "fine_amount")
    private Double fineAmount = 0.0;

    @Column(name = "paid_amount")
    private Double paidAmount = 0.0;

    @Column(name = "balance_amount")
    private Double balanceAmount;

    @Column(name = "status")
    private String status;
    // PENDING
    // PARTIAL
    // PAID
    // OVERDUE

    @Column(name = "remarks")
    private String remarks;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

}