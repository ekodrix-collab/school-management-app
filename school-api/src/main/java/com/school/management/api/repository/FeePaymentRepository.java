package com.school.management.api.repository;

import com.school.management.api.entity.FeePayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FeePaymentRepository extends JpaRepository<FeePayment, Long> {

    Optional<FeePayment> findByPaymentId(String paymentId);

    List<FeePayment> findByStudentFeeId(String studentFeeId);

    List<FeePayment> findBySchoolId(String schoolId);

}