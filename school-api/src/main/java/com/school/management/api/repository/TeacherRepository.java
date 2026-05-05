package com.school.management.api.repository;

import com.school.management.api.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TeacherRepository extends JpaRepository<Teacher,Long> {

    Optional<Teacher> findByTeacherId(UUID teacherId);

    List<Teacher> findAllBySchoolId(String schoolId);

    @Query("SELECT t FROM Teacher t WHERE t.role = 'ROLE_TEACHER'")
    List<Teacher> findAllTeachers();

}
