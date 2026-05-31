package com.school.management.api.repository;

import com.school.management.api.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student,Long> {

    Optional<Student> findByStudentId(String studentId);

    List<Student> findBySchoolId(String schoolId);

    @Query(value = "SELECT COUNT(*) FROM students ", nativeQuery = true)
    Long getStudentCount();

    @Query(value = "SELECT COUNT(*) " +
                    "FROM students " +
                    "WHERE DATE_TRUNC('month', created_at) = DATE_TRUNC('month', CURRENT_DATE)", nativeQuery = true)
    Long getCurrentMonthStudentGrowth();
}
