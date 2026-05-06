package com.school.management.api.repository;

import com.school.management.api.entity.SchoolClass;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SchoolClassRepository extends JpaRepository<SchoolClass,Long> {

    @Modifying
    @Transactional
    @Query("DELETE FROM SchoolClass sc WHERE sc.classId = :classId")
    int deleteByClassId(@Param("classId") String classId);
}
