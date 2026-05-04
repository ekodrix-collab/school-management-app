package com.school.management.api.repository;

import com.school.management.api.entity.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

    Optional<Attendance> findByStudentIdAndDate(String studentId, LocalDate date);

    List<Attendance> findAllByClassIdAndDate(String classId, LocalDate date);

    @Query("SELECT a FROM Attendance a WHERE a.studentId IN :studentIds AND a.date = :date")
    List<Attendance> findByStudentIdsAndDate(
            @Param("studentIds") List<String> studentIds,
            @Param("date") LocalDate date
    );

}
