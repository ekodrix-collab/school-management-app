package com.school.management.api.service;

import com.school.management.api.entity.SchoolClass;
import com.school.management.api.model.requstModel.SchoolClassRequest;
import com.school.management.api.model.responseModel.SchoolClassResponse;
import com.school.management.api.repository.SchoolClassRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SchoolClassService {

    @Autowired
    private SchoolClassRepository schoolClassRepository;

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

    public SchoolClassResponse createClass(SchoolClassRequest request) {

        SchoolClass sc = new SchoolClass();

        sc.setStandard(request.getStandard());
        sc.setDivision(request.getDivision());
        sc.setDisplayName(request.getStandard() + "-" + request.getDivision());
        sc.setClassTeacherId(request.getClassTeacherId());
        sc.setAcademicYearID("2025-2026"); // can be dynamic later
        sc.setIsActive(true);
        sc.setCreatedAt(LocalDateTime.now());
        sc.setUpdatedAt(LocalDateTime.now());

        SchoolClass saved = schoolClassRepository.save(sc);

        return mapToResponse(saved);
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

        res.setId(sc.getId());
        res.setDisplayName(schoolclass.getDisplayName());
        res.setCapacity(schoolclass.getCapacity());
        res.setClassTeacherId(schoolclass.getClassTeacherId());
        res.setAcademicYearID(schoolclass.getAcademicYearID());
        res.setIsActive(schoolclass.getIsActive());
        res.setCreatedAt(schoolclass.getCreatedAt());
        res.setUpdatedAt(schoolclass.getUpdatedAt());

        return res;
    }
}
