package com.school.management.api.service;

import com.school.management.api.entity.FeeStructure;
import com.school.management.api.entity.StudentFee;
import com.school.management.api.model.requstModel.StudentFeeRequest;
import com.school.management.api.model.responseModel.StudentFeeResponse;
import com.school.management.api.repository.FeeStructureRepository;
import com.school.management.api.repository.StudentFeeRepository;
import com.school.management.api.service.authService.AuthUtil;
import com.school.management.api.service.mapper.IdGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudentFeeService {

    private final StudentFeeRepository studentFeeRepository;

    private final FeeStructureRepository feeStructureRepository;

    public StudentFeeResponse createStudentFee(StudentFeeRequest request) {
        String schoolId = AuthUtil.getCurrentSchoolId();
        Boolean alreadyExists = studentFeeRepository
                .existsByFeeStructureIdAndStudentIdAndSchoolId(request.getFeeStructureId(), request.getStudentId(),schoolId);

        if (alreadyExists) {
            throw new RuntimeException("Student fee already assigned");
        }

        FeeStructure feeStructure = feeStructureRepository.findByFeeStructureId(request.getFeeStructureId())
                        .orElseThrow(() -> new RuntimeException("Fee structure not found"));

        Double discount = request.getDiscountAmount() != null
                        ? request.getDiscountAmount()
                        : 0.0;

        Double fine = request.getFineAmount() != null
                        ? request.getFineAmount()
                        : 0.0;

        Double totalAmount = feeStructure.getAmount()
                        + fine
                        - discount;

        StudentFee studentFee = new StudentFee();

        studentFee.setStudentFeeId(IdGenerator.generateStudentId("STD-FEE"));
        studentFee.setFeeStructureId(request.getFeeStructureId());
        studentFee.setSchoolId(schoolId);
        studentFee.setAcademicYearId(feeStructure.getAcademicYearId());
        studentFee.setStudentId(request.getStudentId());
        studentFee.setTotalAmount(totalAmount);
        studentFee.setDiscountAmount(discount);
        studentFee.setFineAmount(fine);
        studentFee.setPaidAmount(0.0);
        studentFee.setBalanceAmount(totalAmount);
        studentFee.setStatus("PENDING");
        studentFee.setRemarks(request.getRemarks());
        studentFee.setCreatedAt(LocalDateTime.now());
        studentFee.setUpdatedAt(LocalDateTime.now());

        StudentFee savedStudentFee = studentFeeRepository.save(studentFee);
        return mapToResponse(savedStudentFee);
    }

    public List<StudentFeeResponse> getStudentFees(String studentId) {

        List<StudentFee> studentFees = studentFeeRepository.findByStudentId(studentId);
        return studentFees.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private StudentFeeResponse mapToResponse(StudentFee studentFee) {

        StudentFeeResponse response = new StudentFeeResponse();

        response.setStudentFeeId(studentFee.getStudentFeeId());
        response.setFeeStructureId(studentFee.getFeeStructureId());
        response.setSchoolId(studentFee.getSchoolId());
        response.setAcademicYearId(studentFee.getAcademicYearId());
        response.setStudentId(studentFee.getStudentId());
        response.setTotalAmount(studentFee.getTotalAmount());
        response.setDiscountAmount(studentFee.getDiscountAmount());
        response.setFineAmount(studentFee.getFineAmount());
        response.setPaidAmount(studentFee.getPaidAmount());
        response.setBalanceAmount(studentFee.getBalanceAmount());
        response.setStatus(studentFee.getStatus());
        response.setRemarks(studentFee.getRemarks());
        response.setCreatedAt(studentFee.getCreatedAt());

        return response;
    }

}