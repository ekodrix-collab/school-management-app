package com.school.management.api.repository;

import com.school.management.api.entity.TeacherClassSubjects;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeacherClassSubjectsRepository extends JpaRepository<TeacherClassSubjects,Long> {

    TeacherClassSubjects findByTeacherClassSubjectId(String teacherClassSubjectId);

    List<TeacherClassSubjects> findBySchoolId(String schoolId);

}
