package com.school.management.api.repository;

import com.school.management.api.entity.StudentAttendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentAttendanceRepository extends JpaRepository<StudentAttendance, Long> {

    List<StudentAttendance> findByAttendanceSessionId(String attendanceSessionId);

    void deleteByAttendanceSessionId(String attendanceSessionId);

    Optional<StudentAttendance> findByAttendanceSessionIdAndStudentId(String attendanceSessionId, String studentId);
}
