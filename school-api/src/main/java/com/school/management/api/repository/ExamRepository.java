package com.school.management.api.repository;

import com.school.management.api.entity.Exam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExamRepository extends JpaRepository<Exam, Long> {
    Optional<Exam> findByExamId(String examId);
    List<Exam> findByExamIdIn(List<String> examIds);
}
