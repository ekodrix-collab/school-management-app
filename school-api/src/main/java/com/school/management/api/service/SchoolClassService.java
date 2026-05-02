package com.school.management.api.service;

import com.school.management.api.entity.SchoolClass;
import com.school.management.api.model.requstModel.SchoolClassRequest;
import com.school.management.api.model.responseModel.SchoolClassResponse;
import com.school.management.api.repository.SchoolClassRepository;

import com.school.management.api.security.CustomUserDetails;
import com.school.management.api.service.authService.AuthUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static com.school.management.api.service.mapper.MapperService.getAcademicYear;

@Service
public class SchoolClassService {

    @Autowired
    private SchoolClassRepository schoolClassRepository;

    public SchoolClassResponse createClass(SchoolClassRequest request) {

        SchoolClass sc = new SchoolClass();

        sc.setStandard(request.getStandard());
        sc.setDivision(request.getDivision());
        sc.setCapacity(request.getCapacity());
        sc.setDisplayName(request.getStandard() + "-" + request.getDivision());
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
        sc.setDisplayName(request.getStandard() + "-" + request.getDivision());
        sc.setClassTeacherId(request.getClassTeacherId());
        sc.setUpdatedAt(LocalDateTime.now());

        SchoolClass updated = schoolClassRepository.save(sc);

        return mapToResponse(updated);
    }

    public void deleteClass(Long id) {
        SchoolClass sc = schoolClassRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Class not found"));

        schoolClassRepository.delete(sc);
    }

    private SchoolClassResponse mapToResponse(SchoolClass schoolclass) {
        SchoolClassResponse res = new SchoolClassResponse();

        res.setDisplayName(schoolclass.getDisplayName());
        res.setCapacity(schoolclass.getCapacity());
        res.setClassTeacherId(schoolclass.getClassTeacherId());
        res.setAcademicYearID(schoolclass.getAcademicYearId());
        res.setIsActive(schoolclass.getIsActive());

        return res;
    }
}
