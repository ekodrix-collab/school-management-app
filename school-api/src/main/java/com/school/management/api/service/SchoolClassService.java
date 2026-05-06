package com.school.management.api.service;

import com.school.management.api.entity.SchoolClass;
import com.school.management.api.exception.BadRequestException;
import com.school.management.api.model.requstModel.SchoolClassRequest;
import com.school.management.api.model.responseModel.SchoolClassResponse;
import com.school.management.api.repository.SchoolClassRepository;
import com.school.management.api.service.authService.AuthUtil;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static com.school.management.api.service.mapper.MapperService.getAcademicYear;

@Service
public class SchoolClassService {

    @Autowired
    private SchoolClassRepository schoolClassRepository;

    @Transactional
    public SchoolClassResponse createClass(SchoolClassRequest request) {

        String schoolId = AuthUtil.getCurrentSchoolId();
        SchoolClass sc = new SchoolClass();

        sc.setSchoolId(schoolId);
        sc.setStandard(request.getStandard());
        sc.setDivision(request.getDivision());
        sc.setCapacity(request.getCapacity());
        sc.setClassId(request.getStandard() + "-" + request.getDivision());
        sc.setClassTeacherId(request.getClassTeacherId());
        sc.setAcademicYearId(getAcademicYear());
        sc.setIsActive(true);
        sc.setCreatedAt(LocalDateTime.now());
        sc.setUpdatedAt(LocalDateTime.now());

        SchoolClass saved = schoolClassRepository.save(sc);
        return mapToResponse(saved);

    }

    public List<SchoolClassResponse> getAllClasses() {
        return schoolClassRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());

    }

    public SchoolClassResponse getClassById(Long id) {
        SchoolClass sc = schoolClassRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Class not found"));
        return mapToResponse(sc);

    }

    public SchoolClassResponse updateClass(Long id, SchoolClassRequest request) {
        SchoolClass sc = schoolClassRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Class not found"));

        sc.setStandard(request.getStandard());
        sc.setDivision(request.getDivision());
        sc.setClassId(request.getStandard() + "-" + request.getDivision());
        sc.setClassTeacherId(request.getClassTeacherId());
        sc.setUpdatedAt(LocalDateTime.now());

        SchoolClass updated = schoolClassRepository.save(sc);

        return mapToResponse(updated);
    }

    public void deleteClass(String classId) {
        int deleted = schoolClassRepository.deleteByClassId(classId);

        if (deleted == 0) {
            throw new RuntimeException("Class not found");
        }
    }

    private SchoolClassResponse mapToResponse(SchoolClass schoolclass) {
        SchoolClassResponse res = new SchoolClassResponse();

        res.setClassId(schoolclass.getClassId());
        res.setDisplayName(schoolclass.getClassId());
        res.setCapacity(schoolclass.getCapacity());
        res.setClassTeacherId(schoolclass.getClassTeacherId());
        res.setAcademicYearID(schoolclass.getAcademicYearId());
        res.setIsActive(schoolclass.getIsActive());
        res.setDivision(schoolclass.getDivision());
        res.setStandard(schoolclass.getStandard());

        return res;
    }
}
