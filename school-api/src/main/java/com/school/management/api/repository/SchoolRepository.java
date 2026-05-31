package com.school.management.api.repository;

import com.school.management.api.entity.School;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SchoolRepository extends JpaRepository<School,Long> {

    boolean existsBySchoolId(String schoolId);

    Optional<School> findBySchoolId(String schoolId);

    @Query(value = "SELECT COUNT(*) FROM schools WHERE is_active = true", nativeQuery = true)
    Long getActiveSchoolCount();

    @Query(value = "SELECT COUNT(*) " +
                    "FROM schools " +
                    "WHERE DATE_TRUNC('month', created_at) = DATE_TRUNC('month', CURRENT_DATE)",
            nativeQuery = true)
    Long getCurrentMonthSchoolGrowth();


}
