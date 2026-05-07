package com.school.management.api.repository;

import com.school.management.api.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Long> {

    Optional<Subject> findBySubjectId(String subjectId);

    List<Subject> findBySubjectIdIn(List<String> subjectIds);

    List<Subject> findAllBySubjectIdIn(List<String> subjectIds);

}
