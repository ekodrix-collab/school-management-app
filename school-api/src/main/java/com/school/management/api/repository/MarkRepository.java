package com.school.management.api.repository;

import com.school.management.api.entity.Mark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MarkRepository extends JpaRepository<Mark, Long> {

    Optional<Mark> findByStudentIdAndSubjectIdAndExamIdAndAcademicYearId(
            String studentId, String subjectId, String examId, String academicYearId
    );

    List<Mark> findByStudentIdInAndSubjectIdInAndExamIdInAndAcademicYearIdIn(
            List<String> studentIds,
            List<String> subjectIds,
            List<String> examIds,
            List<String> academicYearIds
    );

}
