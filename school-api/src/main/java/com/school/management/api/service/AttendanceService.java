package com.school.management.api.service;

import com.school.management.api.entity.AttendanceSession;
import com.school.management.api.entity.StudentAttendance;
import com.school.management.api.model.requstModel.AttendanceRequest;
import com.school.management.api.model.requstModel.AttendanceStudentRequest;
import com.school.management.api.model.responseModel.AttendanceDetailsResponse;
import com.school.management.api.model.responseModel.AttendanceResponse;
import com.school.management.api.model.responseModel.StudentAttendanceResponse;
import com.school.management.api.repository.AttendanceSessionRepository;
import com.school.management.api.repository.StudentAttendanceRepository;
import com.school.management.api.service.authService.AuthUtil;
import com.school.management.api.service.mapper.IdGenerator;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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
        boolean exists = attendanceSessionRepository.existsBySchoolIdAndAcademicYearIdAndClassIdAndAttendanceDateAndSessionTypeAndPeriodNumber(
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

    //one by one attendance option

    public List<AttendanceDetailsResponse> getAllAttendance() {
        String schoolId = AuthUtil.getCurrentSchoolId();
        List<AttendanceSession> sessions = attendanceSessionRepository.findAllBySchoolId(schoolId);
        return sessions.stream().map(this::mapToDetailsResponse).toList();
    }

    public AttendanceDetailsResponse getAttendanceById(String attendanceSessionId) {
        AttendanceSession session = attendanceSessionRepository.findByAttendanceSessionId(attendanceSessionId)
                .orElseThrow(() -> new RuntimeException("Attendance session not found with id: " + attendanceSessionId));
        return mapToDetailsResponse(session);
    }

    public List<AttendanceDetailsResponse> getAttendanceWithFilters(String classId, String academicYearId, LocalDate attendanceDate) {
        String schoolId = AuthUtil.getCurrentSchoolId();
        List<AttendanceSession> sessions = attendanceSessionRepository.findWithFilters(schoolId, classId, academicYearId, attendanceDate);
        return sessions.stream().map(this::mapToDetailsResponse).toList();
    }

    @Transactional
    public AttendanceResponse updateAttendance(String attendanceSessionId, AttendanceRequest request) {
        AttendanceSession session = attendanceSessionRepository.findByAttendanceSessionId(attendanceSessionId)
                .orElseThrow(() -> new RuntimeException("Attendance session not found with id: " + attendanceSessionId));

        session.setTimetableId(request.getTimetableId());
        session.setClassSubjectId(request.getClassSubjectId());
        session.setTeacherId(request.getTeacherId());
        session.setAttendanceDate(request.getAttendanceDate());
        session.setSessionType(request.getSessionType());
        session.setPeriodNumber(request.getPeriodNumber());
        session.setIsSubstitution(request.getIsSubstitution());
        session.setOriginalClassSubjectId(request.getOriginalClassSubjectId());
        session.setOriginalTeacherId(request.getOriginalTeacherId());
        session.setRemarks(request.getRemarks());
        session.setUpdatedAt(LocalDateTime.now());

        attendanceSessionRepository.save(session);

        // Update or sync student attendance records in-place
        if (request.getStudents() != null) {
            // Get all existing student attendances for this session
            List<StudentAttendance> existingAttendances = studentAttendanceRepository.findByAttendanceSessionId(attendanceSessionId);

            // Collect the student IDs in the new request
            List<String> requestStudentIds = request.getStudents().stream()
                    .map(AttendanceStudentRequest::getStudentId)
                    .toList();

            // Delete any existing student attendances that are NOT in the request
            List<StudentAttendance> toDelete = existingAttendances.stream()
                    .filter(sa -> !requestStudentIds.contains(sa.getStudentId()))
                    .toList();
            if (!toDelete.isEmpty()) {
                studentAttendanceRepository.deleteAll(toDelete);
            }

            List<StudentAttendance> studentAttendancesToSave = new ArrayList<>();
            for (AttendanceStudentRequest studentRequest : request.getStudents()) {
                StudentAttendance studentAttendance = existingAttendances.stream()
                        .filter(sa -> sa.getStudentId().equals(studentRequest.getStudentId()))
                        .findFirst()
                        .orElse(null);

                if (studentAttendance == null) {
                    studentAttendance = new StudentAttendance();
                    studentAttendance.setStudentAttendanceId(IdGenerator.generateStudentId("STA"));
                    studentAttendance.setAttendanceSessionId(attendanceSessionId);
                    studentAttendance.setStudentId(studentRequest.getStudentId());
                    studentAttendance.setCreatedAt(LocalDateTime.now());
                }

                studentAttendance.setStatus(studentRequest.getStatus());
                studentAttendance.setRemarks(studentRequest.getRemarks());
                studentAttendance.setUpdatedAt(LocalDateTime.now());
                studentAttendancesToSave.add(studentAttendance);
            }
            studentAttendanceRepository.saveAll(studentAttendancesToSave);
        }

        return AttendanceResponse.builder()
                .attendanceSessionId(attendanceSessionId)
                .message("Attendance updated successfully")
                .build();
    }

    @Transactional
    public String deleteAttendance(String attendanceSessionId) {
        AttendanceSession session = attendanceSessionRepository.findByAttendanceSessionId(attendanceSessionId)
                .orElseThrow(() -> new RuntimeException("Attendance session not found with id: " + attendanceSessionId));

        studentAttendanceRepository.deleteByAttendanceSessionId(attendanceSessionId);
        attendanceSessionRepository.delete(session);

        return "Attendance session deleted successfully";
    }

    private AttendanceDetailsResponse mapToDetailsResponse(AttendanceSession session) {
        List<StudentAttendance> studentAttendances = studentAttendanceRepository.findByAttendanceSessionId(session.getAttendanceSessionId());

        List<StudentAttendanceResponse> studentResponses = studentAttendances.stream()
                .map(sa -> StudentAttendanceResponse.builder()
                        .studentAttendanceId(sa.getStudentAttendanceId())
                        .studentId(sa.getStudentId())
                        .status(sa.getStatus())
                        .remarks(sa.getRemarks())
                        .build())
                .toList();

        return AttendanceDetailsResponse.builder()
                .attendanceSessionId(session.getAttendanceSessionId())
                .schoolId(session.getSchoolId())
                .academicYearId(session.getAcademicYearId())
                .classId(session.getClassId())
                .classSubjectId(session.getClassSubjectId())
                .timetableId(session.getTimetableId())
                .teacherId(session.getTeacherId())
                .attendanceDate(session.getAttendanceDate())
                .sessionType(session.getSessionType())
                .periodNumber(session.getPeriodNumber())
                .isSubstitution(session.getIsSubstitution())
                .originalClassSubjectId(session.getOriginalClassSubjectId())
                .originalTeacherId(session.getOriginalTeacherId())
                .remarks(session.getRemarks())
                .createdAt(session.getCreatedAt())
                .updatedAt(session.getUpdatedAt())
                .students(studentResponses)
                .build();
    }
}
