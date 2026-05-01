package com.school.management.repository;

import com.school.management.entity.StudentEnrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentEnrollmentRepository extends JpaRepository<StudentEnrollment, Long> {
    List<StudentEnrollment> findByClassSectionId(Long classSectionId);
    List<StudentEnrollment> findByStudentId(Long studentId);
}
