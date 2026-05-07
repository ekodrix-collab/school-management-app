package com.school.management.api.repository;

import com.school.management.api.entity.ClassSubject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClassSubjectRepository extends JpaRepository<ClassSubject,Long> {

    @Query(value = "SELECT * FROM class_subjects WHERE school_id = :schoolId", nativeQuery = true)
    List<ClassSubject> getAllClassSubject(@Param("schoolId") String schoolId);
}
