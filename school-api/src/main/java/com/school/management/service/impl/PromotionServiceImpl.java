package com.school.management.service.impl;

import com.school.management.entity.ClassSection;
import com.school.management.entity.StudentEnrollment;
import com.school.management.exception.ResourceNotFoundException;
import com.school.management.repository.ClassSectionRepository;
import com.school.management.repository.StudentEnrollmentRepository;
import com.school.management.service.PromotionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PromotionServiceImpl implements PromotionService {

    private final StudentEnrollmentRepository studentEnrollmentRepository;
    private final ClassSectionRepository classSectionRepository;

    @Override
    @Transactional
    public void promoteStudents(List<Long> studentEnrollmentIds, Long targetClassSectionId) {
        ClassSection targetSection = classSectionRepository.findById(targetClassSectionId)
                .orElseThrow(() -> new ResourceNotFoundException("Target ClassSection not found"));

        List<StudentEnrollment> currentEnrollments = studentEnrollmentRepository.findAllById(studentEnrollmentIds);

        List<StudentEnrollment> newEnrollments = currentEnrollments.stream()
                .map(oldEnrollment -> StudentEnrollment.builder()
                        .student(oldEnrollment.getStudent())
                        .classSection(targetSection)
                        .academicYearId(targetSection.getAcademicYearId())
                        .schoolId(oldEnrollment.getSchoolId())
                        .build())
                .collect(Collectors.toList());

        studentEnrollmentRepository.saveAll(newEnrollments);
        
        // IMPORTANT: We do not update or delete oldEnrollment. 
        // This maintains historical records as required.
    }
}
