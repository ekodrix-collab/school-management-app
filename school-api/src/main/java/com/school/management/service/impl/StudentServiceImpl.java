package com.school.management.service.impl;

import com.school.management.entity.Admission;
import com.school.management.entity.Student;
import com.school.management.entity.StudentEnrollment;
import com.school.management.entity.ClassSection;
import com.school.management.repository.StudentRepository;
import com.school.management.repository.StudentEnrollmentRepository;
import com.school.management.repository.ClassSectionRepository;
import com.school.management.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl {

    private final StudentRepository studentRepository;
    private final StudentEnrollmentRepository studentEnrollmentRepository;
    private final ClassSectionRepository classSectionRepository;

    @Transactional
    public Student createFromAdmission(Admission admission, Long targetClassSectionId) {
        // 1. Create Student record
        Student student = Student.builder()
                .name(admission.getStudentName())
                .dateOfBirth(admission.getDateOfBirth())
                .gender(admission.getGender())
                .admissionNumber("ADM-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase())
                .rollNumber("TEMP") // To be assigned during enrollment
                .build();
        
        student = studentRepository.save(student);

        // 2. Enroll student in the target class section for the current academic year
        ClassSection section = classSectionRepository.findById(targetClassSectionId)
                .orElseThrow(() -> new ResourceNotFoundException("Target ClassSection not found"));

        StudentEnrollment enrollment = StudentEnrollment.builder()
                .student(student)
                .classSection(section)
                .academicYearId(section.getAcademicYearId())
                .schoolId(admission.getSchoolId())
                .build();

        studentEnrollmentRepository.save(enrollment);

        return student;
    }
}
