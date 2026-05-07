package com.school.management.api.service;

import com.school.management.api.constants.Constants;
import com.school.management.api.entity.AcademicYear;
import com.school.management.api.exception.BadRequestException;
import com.school.management.api.model.requstModel.AcademicYearRequestDto;
import com.school.management.api.model.responseModel.AcademicYearResponseDto;
import com.school.management.api.repository.AcademicYearRepository;
import com.school.management.api.service.authService.AuthUtil;
import com.school.management.api.service.mapper.IdGenerator;
import com.school.management.api.service.mapper.MapperService;
import jakarta.persistence.Access;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AcademicYearService {

    private final AcademicYearRepository academicYearRepository;

    @Autowired
    MapperService mapperService;

    @Transactional
    public AcademicYearResponseDto createAcademicYear(AcademicYearRequestDto requestDto) {
        String schoolId = AuthUtil.getCurrentSchoolId();

        if (requestDto.getStartDate().isAfter(requestDto.getEndDate())) {
            throw new BadRequestException("Start date cannot be greater than end date");
        }

        String academicName = mapperService.buildAcademicYear(requestDto.getStartDate(),requestDto.getEndDate());

        if (academicYearRepository.existsByAcademicYear(academicName, schoolId)) {
            throw new BadRequestException("Academic year with name " + requestDto.getName() + " already exists for this school");
        }


        AcademicYear academicYear = new AcademicYear();
        academicYear.setSchoolId(schoolId);
        academicYear.setName(academicName);
        academicYear.setStatus(Constants.ACTIVE);
        academicYear.setAcademicYearId(IdGenerator.generateStudentId("AY"));
        academicYear.setStartDate(requestDto.getStartDate());
        academicYear.setEndDate(requestDto.getEndDate());
        academicYear.setCreatedAt(LocalDateTime.now(ZoneId.of(Constants.INDIAN_TIME)));
        academicYear.setUpdatedAt(LocalDateTime.now(ZoneId.of(Constants.INDIAN_TIME)));

        AcademicYear savedAcademicYear = academicYearRepository.save(academicYear);
        return mapperService.toAcademicYearResponseDto(savedAcademicYear);
    }

    public List<AcademicYearResponseDto> getAllAcademicYears() {
        String schoolId = AuthUtil.getCurrentSchoolId();
        List<AcademicYear> academicYears = academicYearRepository.getAllAcademicYear(schoolId);
        return mapperService.toAcademicYearResponseDtoList(academicYears);
    }

    public AcademicYearResponseDto getAcademicYearById(String academicYearId) {
        AcademicYear academicYear = academicYearRepository.findByAcademicYearId(academicYearId)
                .orElseThrow(() -> new BadRequestException("Academic year not found with id: " + academicYearId));
        return mapperService.toAcademicYearResponseDto(academicYear);
    }

    @Transactional
    public AcademicYearResponseDto updateAcademicYear(String academicYearId, AcademicYearRequestDto requestDto) {
        String schoolId = AuthUtil.getCurrentSchoolId();
        AcademicYear academicYear = academicYearRepository.findByAcademicYearId(academicYearId)
                .orElseThrow(() -> new BadRequestException("Academic year not found with id: " + academicYearId));

        if (!academicYear.getName().equalsIgnoreCase(requestDto.getName()) &&
                academicYearRepository.existsByAcademicYear(requestDto.getName(), schoolId)) {
            throw new BadRequestException("Academic year with name " + requestDto.getName() + " already exists for this school");
        }

        academicYear.setName(requestDto.getName());
        academicYear.setStartDate(requestDto.getStartDate());
        academicYear.setEndDate(requestDto.getEndDate());
        academicYear.setStatus(requestDto.getStatus());
        academicYear.setUpdatedAt(LocalDateTime.now(ZoneId.of(Constants.INDIAN_TIME)));

        AcademicYear updatedAcademicYear = academicYearRepository.save(academicYear);
        return mapperService.toAcademicYearResponseDto(updatedAcademicYear);
    }

    @Transactional
    public String deleteAcademicYear(String academicYearId) {
        AcademicYear academicYear = academicYearRepository.findByAcademicYearId(academicYearId)
                .orElseThrow(() -> new BadRequestException("Academic year not found with id: " + academicYearId));

        academicYearRepository.delete(academicYear);
        return "Academic year deleted successfully";
    }


//    private String getFallbackAcademicYear() {
//        LocalDateTime now = LocalDateTime.now(ZoneId.of(Constants.INDIAN_TIME));
//        int year = now.getYear();
//        int month = now.getMonthValue();
//
//        if (month < 6) {
//            return (year - 1) + "-" + year;
//        } else {
//            return year + "-" + (year + 1);
//        }
//    }
    
}
