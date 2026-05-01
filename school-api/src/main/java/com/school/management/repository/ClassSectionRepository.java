package com.school.management.repository;

import com.school.management.entity.ClassSection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClassSectionRepository extends JpaRepository<ClassSection, Long> {
    List<ClassSection> findByAcademicYearId(Long academicYearId);
}
