package com.school.management.api.repository;

import com.school.management.api.entity.AcademicYear;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AcademicYearRepository extends JpaRepository<AcademicYear, Long> {

    Optional<AcademicYear> findByAcademicYearId(String academicYearId);

    boolean existsByName(String name);

    @Query("SELECT a FROM AcademicYear a WHERE a.name = :year AND a.schoolId = :schoolId")
    AcademicYear findByAcademicYear(@Param("year") String year,
            @Param("schoolId") String schoolId);

    @Query("SELECT a FROM AcademicYear a WHERE a.schoolId = :schoolId")
    List<AcademicYear> getAllAcademicYear(@Param("schoolId") String schoolId);

    @Query("SELECT COUNT(a) > 0 FROM AcademicYear a WHERE a.name = :name AND a.schoolId = :schoolId")
    boolean existsByAcademicYear(@Param("name") String name,
            @Param("schoolId") String schoolId);


    AcademicYear findActiveBySchoolId(String schoolId);

    List<AcademicYear> findAllByAcademicYearIdIn(List<String> academicYearIds);
}

