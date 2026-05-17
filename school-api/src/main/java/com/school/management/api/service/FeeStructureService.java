package com.school.management.api.service;

import com.school.management.api.entity.FeeStructure;
import com.school.management.api.model.requstModel.FeeStructureRequest;
import com.school.management.api.model.responseModel.FeeStructureResponse;
import com.school.management.api.repository.FeeStructureRepository;
import com.school.management.api.service.authService.AuthUtil;
import com.school.management.api.service.mapper.IdGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FeeStructureService {

    private final FeeStructureRepository feeStructureRepository;

    public FeeStructureResponse createFeeStructure(FeeStructureRequest request) {
        String schoolId = AuthUtil.getCurrentSchoolId();
        Boolean alreadyExists = feeStructureRepository.existsBySchoolIdAndAcademicYearIdAndClassIdAndFeeName(
                                schoolId,
                                request.getAcademicYearId(),
                                request.getClassId(),
                                request.getFeeName());

        if (alreadyExists) {
            throw new RuntimeException("Fee structure already exists");
        }

        FeeStructure feeStructure = new FeeStructure();

        feeStructure.setFeeStructureId(IdGenerator.generateStudentId("FEE-STR"));
        feeStructure.setSchoolId(schoolId);
        feeStructure.setAcademicYearId(request.getAcademicYearId());
        feeStructure.setClassId(request.getClassId());
        feeStructure.setFeeName(request.getFeeName());
        feeStructure.setFeeType(request.getFeeType());
        feeStructure.setAmount(request.getAmount());
        feeStructure.setDueDate(request.getDueDate());
        feeStructure.setDescription(request.getDescription());
        feeStructure.setIsActive(true);
        feeStructure.setCreatedAt(LocalDateTime.now());
        feeStructure.setUpdatedAt(LocalDateTime.now());

        FeeStructure saveFeeStructure = feeStructureRepository.save(feeStructure);
        return mapToResponse(saveFeeStructure);

    }

    public List<FeeStructureResponse> getFeeStructures(String academicYearId, String classId) {
        String schoolId = AuthUtil.getCurrentSchoolId();
        List<FeeStructure> feeStructures = feeStructureRepository.findBySchoolIdAndAcademicYearIdAndClassId(
                                schoolId,
                                academicYearId,
                                classId);

        return feeStructures.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private FeeStructureResponse mapToResponse(FeeStructure feeStructure) {

        FeeStructureResponse response = new FeeStructureResponse();

        response.setFeeStructureId(feeStructure.getFeeStructureId());
        response.setSchoolId(feeStructure.getSchoolId());
        response.setAcademicYearId(feeStructure.getAcademicYearId());
        response.setClassId(feeStructure.getClassId());
        response.setFeeName(feeStructure.getFeeName());
        response.setFeeType(feeStructure.getFeeType());
        response.setAmount(feeStructure.getAmount());
        response.setDueDate(feeStructure.getDueDate());
        response.setDescription(feeStructure.getDescription());
        response.setIsActive(feeStructure.getIsActive());
        response.setCreatedAt(feeStructure.getCreatedAt());

        return response;
    }

}