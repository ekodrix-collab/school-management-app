package com.school.management.api.repository;

import com.school.management.api.entity.StudentMark;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StudentMarkRepository extends JpaRepository<StudentMark, Long> {

    Optional<StudentMark> findByStudentMarkId(String studentMarkId);

    Boolean existsByExamSubjectIdAndStudentIdAndSchoolId(String examSubjectId, String studentId,String schoolId);

    List<StudentMark> findByExamSubjectId(String examSubjectId);

    List<StudentMark> findByStudentId(String studentId);

    List<StudentMark> findBySchoolId(String schoolId);

}