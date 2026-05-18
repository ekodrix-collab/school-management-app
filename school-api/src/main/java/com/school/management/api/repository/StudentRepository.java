package com.school.management.api.repository;

import com.school.management.api.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student,Long> {

    Optional<Student> findByStudentId(String studentId);

}
