package com.school.management.api.service;

import com.school.management.api.entity.FeePayment;
import com.school.management.api.entity.StudentFee;
import com.school.management.api.model.requstModel.FeePaymentRequest;
import com.school.management.api.model.responseModel.FeePaymentResponse;
import com.school.management.api.repository.FeePaymentRepository;
import com.school.management.api.repository.StudentFeeRepository;
import com.school.management.api.service.authService.AuthUtil;
import com.school.management.api.service.mapper.IdGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FeePaymentService {

    private final FeePaymentRepository feePaymentRepository;

    private final StudentFeeRepository studentFeeRepository;

    public FeePaymentResponse createFeePayment(FeePaymentRequest request) {
        String schoolId = AuthUtil.getCurrentSchoolId();
        StudentFee studentFee = studentFeeRepository.findByStudentFeeId(request.getStudentFeeId())
                        .orElseThrow(() -> new RuntimeException("Student fee not found"));

        if (request.getPaidAmount() > studentFee.getBalanceAmount()) {
            throw new RuntimeException("Paid amount exceeds balance amount");
        }

        FeePayment feePayment = new FeePayment();

        feePayment.setPaymentId(IdGenerator.generateStudentId("PAY"));
        feePayment.setStudentFeeId(request.getStudentFeeId());
        feePayment.setSchoolId(schoolId);
        feePayment.setStudentId(studentFee.getStudentId());
        feePayment.setPaidAmount(request.getPaidAmount());
        feePayment.setPaymentDate(LocalDate.now());
        feePayment.setPaymentMethod(request.getPaymentMethod());
        feePayment.setTransactionReference(request.getTransactionReference());
        feePayment.setRemarks(request.getRemarks());
        feePayment.setCreatedAt(LocalDateTime.now());
        feePayment.setUpdatedAt(LocalDateTime.now());

        FeePayment savedPayment = feePaymentRepository.save(feePayment);

        Double updatedPaidAmount = studentFee.getPaidAmount() + request.getPaidAmount();

        Double updatedBalance = studentFee.getTotalAmount() - updatedPaidAmount;

        studentFee.setPaidAmount(updatedPaidAmount);
        studentFee.setBalanceAmount(updatedBalance);

        if (updatedBalance <= 0) {
            studentFee.setStatus("PAID");
        } else {
            studentFee.setStatus("PARTIAL");
        }

        studentFee.setUpdatedAt(LocalDateTime.now());
        studentFeeRepository.save(studentFee);

        return mapToResponse(savedPayment);
    }

    public List<FeePaymentResponse> getPayments(String studentFeeId) {
        List<FeePayment> payments = feePaymentRepository.findByStudentFeeId(studentFeeId);
        return payments.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public List<FeePaymentResponse> getAllPayments() {
        String schoolId = AuthUtil.getCurrentSchoolId();
        List<FeePayment> payments = feePaymentRepository.findBySchoolId(schoolId);
        return payments.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public FeePaymentResponse updateFeePayment(String paymentId, FeePaymentRequest request) {
        String schoolId = AuthUtil.getCurrentSchoolId();
        FeePayment feePayment = feePaymentRepository.findByPaymentId(paymentId)
                .orElseThrow(() -> new RuntimeException("Fee payment not found"));

        if (!feePayment.getSchoolId().equals(schoolId)) {
            throw new RuntimeException("Unauthorized to update this fee payment");
        }

        StudentFee studentFee = studentFeeRepository.findByStudentFeeId(feePayment.getStudentFeeId())
                .orElseThrow(() -> new RuntimeException("Student fee not found"));

        Double previousPaidAmount = feePayment.getPaidAmount();
        Double newPaidAmountRequest = request.getPaidAmount();
        Double diff = newPaidAmountRequest - previousPaidAmount;
        
        if (diff > studentFee.getBalanceAmount()) {
            throw new RuntimeException("Updated paid amount exceeds total balance amount");
        }

        feePayment.setPaidAmount(newPaidAmountRequest);
        feePayment.setPaymentMethod(request.getPaymentMethod());
        feePayment.setTransactionReference(request.getTransactionReference());
        feePayment.setRemarks(request.getRemarks());
        feePayment.setUpdatedAt(LocalDateTime.now());

        FeePayment savedPayment = feePaymentRepository.save(feePayment);

        Double updatedPaidAmount = studentFee.getPaidAmount() + diff;
        Double updatedBalance = studentFee.getTotalAmount() - updatedPaidAmount;

        studentFee.setPaidAmount(updatedPaidAmount);
        studentFee.setBalanceAmount(updatedBalance);

        if (updatedBalance <= 0) {
            studentFee.setStatus("PAID");
        } else if (updatedPaidAmount <= 0) {
            studentFee.setStatus("PENDING");
        } else {
            studentFee.setStatus("PARTIAL");
        }

        studentFee.setUpdatedAt(LocalDateTime.now());
        studentFeeRepository.save(studentFee);

        return mapToResponse(savedPayment);
    }

    public void deleteFeePayment(String paymentId) {
        String schoolId = AuthUtil.getCurrentSchoolId();
        FeePayment feePayment = feePaymentRepository.findByPaymentId(paymentId)
                .orElseThrow(() -> new RuntimeException("Fee payment not found"));

        if (!feePayment.getSchoolId().equals(schoolId)) {
            throw new RuntimeException("Unauthorized to delete this fee payment");
        }

        StudentFee studentFee = studentFeeRepository.findByStudentFeeId(feePayment.getStudentFeeId())
                .orElseThrow(() -> new RuntimeException("Student fee not found"));

        Double previousPaidAmount = feePayment.getPaidAmount();
        Double updatedPaidAmount = studentFee.getPaidAmount() - previousPaidAmount;
        Double updatedBalance = studentFee.getTotalAmount() - updatedPaidAmount;

        studentFee.setPaidAmount(updatedPaidAmount);
        studentFee.setBalanceAmount(updatedBalance);

        if (updatedPaidAmount <= 0) {
            studentFee.setStatus("PENDING");
        } else if (updatedBalance <= 0) {
            studentFee.setStatus("PAID");
        } else {
            studentFee.setStatus("PARTIAL");
        }

        studentFee.setUpdatedAt(LocalDateTime.now());
        studentFeeRepository.save(studentFee);

        feePaymentRepository.delete(feePayment);
    }

    private FeePaymentResponse mapToResponse(FeePayment feePayment) {

        FeePaymentResponse response = new FeePaymentResponse();
        response.setPaymentId(feePayment.getPaymentId());
        response.setStudentFeeId(feePayment.getStudentFeeId());
        response.setSchoolId(feePayment.getSchoolId());
        response.setStudentId(feePayment.getStudentId());
        response.setPaidAmount(feePayment.getPaidAmount());
        response.setPaymentDate(feePayment.getPaymentDate());
        response.setPaymentMethod(feePayment.getPaymentMethod());
        response.setTransactionReference(feePayment.getTransactionReference());
        response.setRemarks(feePayment.getRemarks());
        response.setCreatedAt(feePayment.getCreatedAt());

        return response;
    }

}