package com.school.management.api.repository;

import com.school.management.api.entity.FeeStructure;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FeeStructureRepository extends JpaRepository<FeeStructure, Long> {

    Optional<FeeStructure> findByFeeStructureId(String feeStructureId);

    Boolean existsBySchoolIdAndAcademicYearIdAndClassIdAndFeeName(String schoolId, String academicYearId, String classId, String feeName);

    List<FeeStructure> findBySchoolIdAndAcademicYearIdAndClassId(String schoolId, String academicYearId, String classId);

}