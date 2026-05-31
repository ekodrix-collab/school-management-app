package com.school.management.api.repository;

import com.school.management.api.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TeacherRepository extends JpaRepository<Teacher,Long> {

    Optional<Teacher> findByTeacherId(UUID teacherId);

    List<Teacher> findAllBySchoolId(String schoolId);

    @Query("SELECT t FROM Teacher t WHERE t.schoolId = :schoolId AND t.isActive = true")
    List<Teacher> findAllTeachers(@Param("schoolId") String schoolId);

    @Query(value = "SELECT COUNT(*) FROM teachers ", nativeQuery = true)
    Long getTeacherCount();

    @Query(value = "SELECT COUNT(*) " +
                    "FROM teachers " +
                    "WHERE DATE_TRUNC('month', created_at) = DATE_TRUNC('month', CURRENT_DATE)", nativeQuery = true)
    Long getCurrentMonthTeacherGrowth();

}
