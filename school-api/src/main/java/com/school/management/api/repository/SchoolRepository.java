package com.school.management.api.repository;

import com.school.management.api.entity.School;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SchoolRepository extends JpaRepository<School,Long> {

    boolean existsBySchoolId(String schoolId);
}
