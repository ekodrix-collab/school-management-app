package com.school.management.api.repository;

import com.school.management.api.entity.AttendanceSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface AttendanceSessionRepository extends JpaRepository<AttendanceSession, Long> {

    Optional<AttendanceSession> findByAttendanceSessionId(String attendanceSessionId);

    boolean existsBySchoolIdAndAcademicYearIdAndClassIdAndAttendanceDateAndSessionTypeAndPeriodNumber(
            String schoolId,
            String academicYearId,
            String classId,
            LocalDate attendanceDate,
            String sessionType,
            Integer periodNumber
    );

    List<AttendanceSession> findAllBySchoolId(String schoolId);

    @Query("SELECT a FROM AttendanceSession a WHERE a.schoolId = :schoolId " +
           "AND (:classId IS NULL OR a.classId = :classId) " +
           "AND (:academicYearId IS NULL OR a.academicYearId = :academicYearId) " +
           "AND (:attendanceDate IS NULL OR a.attendanceDate = :attendanceDate)")
    List<AttendanceSession> findWithFilters(
            @Param("schoolId") String schoolId,
            @Param("classId") String classId,
            @Param("academicYearId") String academicYearId,
            @Param("attendanceDate") LocalDate attendanceDate
    );
}