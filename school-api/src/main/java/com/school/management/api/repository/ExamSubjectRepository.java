package com.school.management.api.repository;

import com.school.management.api.entity.ExamSubject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExamSubjectRepository extends JpaRepository<ExamSubject, Long> {

    Optional<ExamSubject> findByExamSubjectIdAndSchoolId(String examSubjectId,String schoolId);

    Boolean existsByExamIdAndClassIdAndClassSubjectIdAndSchoolId(String examId, String classId, String classSubjectId,String schoolId);

    List<ExamSubject> findByExamIdAndClassId(String examId, String classId);

}