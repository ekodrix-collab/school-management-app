package com.school.management.repository;

import com.school.management.entity.TeacherAssignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeacherAssignmentRepository extends JpaRepository<TeacherAssignment, Long> {
    List<TeacherAssignment> findByClassSectionId(Long classSectionId);
    List<TeacherAssignment> findByTeacherId(Long teacherId);
    List<TeacherAssignment> findByAcademicYearId(Long academicYearId);
}
