package com.school.management.api.repository;

import com.school.management.api.entity.AttendanceSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
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
}