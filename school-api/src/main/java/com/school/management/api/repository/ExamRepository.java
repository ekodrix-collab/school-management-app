package com.school.management.api.repository;

import com.school.management.api.entity.Exam;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ExamRepository extends JpaRepository<Exam, Long> {

    Optional<Exam> findByExamId(String examId);

    Boolean existsBySchoolIdAndAcademicYearIdAndExamName(
            String schoolId,
            String academicYearId,
            String examName
    );

    List<Exam> findBySchoolIdAndAcademicYearId(
            String schoolId,
            String academicYearId
    );

}
