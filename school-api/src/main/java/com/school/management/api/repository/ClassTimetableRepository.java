package com.school.management.api.repository;

import com.school.management.api.entity.ClassTimetable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ClassTimetableRepository extends JpaRepository<ClassTimetable, Long> {

    Optional<ClassTimetable> findByTimetableId(String timetableId);

    boolean existsBySchoolIdAndAcademicYearIdAndClassIdAndDayNameAndPeriodNumber(
            String schoolId,
            String academicYearId,
            String classId,
            String dayName,
            Integer periodNumber
    );

    List<ClassTimetable> findBySchoolIdAndAcademicYearIdAndClassIdAndDayNameOrderByPeriodNumberAsc(
            String schoolId,
            String academicYearId,
            String classId,
            String dayName
    );

    List<ClassTimetable> findByTeacherIdAndDayName(UUID teacherId, String dayName);

    Optional<ClassTimetable> findBySchoolIdAndAcademicYearIdAndClassIdAndDayNameAndPeriodNumber(
            String schoolId,
            String academicYearId,
            String classId,
            String dayName,
            Integer periodNumber
    );

    List<ClassTimetable> findByStartTimeLessThanEqualAndEndTimeGreaterThanEqual(LocalTime startTime, LocalTime endTime);
}
