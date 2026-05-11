package com.school.management.api.service;

import com.school.management.api.entity.AttendanceSession;
import com.school.management.api.entity.StudentAttendance;
import com.school.management.api.model.requstModel.AttendanceRequest;
import com.school.management.api.model.requstModel.AttendanceStudentRequest;
import com.school.management.api.model.responseModel.AttendanceResponse;
import com.school.management.api.repository.AttendanceSessionRepository;
import com.school.management.api.repository.StudentAttendanceRepository;
import com.school.management.api.service.authService.AuthUtil;
import com.school.management.api.service.mapper.IdGenerator;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class AttendanceService {

    @Autowired
    AttendanceSessionRepository attendanceSessionRepository;

    @Autowired
    StudentAttendanceRepository studentAttendanceRepository;

    @Transactional
    public AttendanceResponse createAttendance(AttendanceRequest request) {

        String schoolId = AuthUtil.getCurrentSchoolId();
        boolean exists = attendanceSessionRepository
                .existsBySchoolIdAndAcademicYearIdAndClassIdAndAttendanceDateAndSessionTypeAndPeriodNumber(
                        request.getSchoolId(),
                        request.getAcademicYearId(),
                        request.getClassId(),
                        request.getAttendanceDate(),
                        request.getSessionType(),
                        request.getPeriodNumber()
                );

        if (exists) {
            throw new RuntimeException("Attendance already marked");
        }

        AttendanceSession attendanceSession = new AttendanceSession();

        attendanceSession.setAttendanceSessionId(IdGenerator.generateStudentId("ATS"));
        attendanceSession.setSchoolId(schoolId);
        attendanceSession.setTimetableId(request.getTimetableId());
        attendanceSession.setAcademicYearId(request.getAcademicYearId());
        attendanceSession.setClassId(request.getClassId());
        attendanceSession.setClassSubjectId(request.getClassSubjectId());
        attendanceSession.setTeacherId(request.getTeacherId());
        attendanceSession.setAttendanceDate(request.getAttendanceDate());
        attendanceSession.setSessionType(request.getSessionType());
        attendanceSession.setPeriodNumber(request.getPeriodNumber());
        attendanceSession.setIsSubstitution(request.getIsSubstitution());
        attendanceSession.setOriginalClassSubjectId(request.getOriginalClassSubjectId());
        attendanceSession.setOriginalTeacherId(request.getOriginalTeacherId());
        attendanceSession.setRemarks(request.getRemarks());
        attendanceSession.setCreatedAt(LocalDateTime.now());
        attendanceSession.setUpdatedAt(LocalDateTime.now());

        attendanceSessionRepository.save(attendanceSession);

        List<StudentAttendance> studentAttendances = new ArrayList<>();

        for (AttendanceStudentRequest studentRequest : request.getStudents()) {

            StudentAttendance studentAttendance = new StudentAttendance();

            studentAttendance.setStudentAttendanceId(IdGenerator.generateStudentId("STA"));
            studentAttendance.setAttendanceSessionId(attendanceSession.getAttendanceSessionId());
            studentAttendance.setStudentId(studentRequest.getStudentId());
            studentAttendance.setStatus(studentRequest.getStatus());
            studentAttendance.setRemarks(studentRequest.getRemarks());
            studentAttendance.setCreatedAt(LocalDateTime.now());
            studentAttendance.setUpdatedAt(LocalDateTime.now());

            studentAttendances.add(studentAttendance);
        }

        studentAttendanceRepository.saveAll(studentAttendances);

        return AttendanceResponse.builder()
                .attendanceSessionId(attendanceSession.getAttendanceSessionId())
                .message("Attendance created successfully")
                .build();
    }

}
