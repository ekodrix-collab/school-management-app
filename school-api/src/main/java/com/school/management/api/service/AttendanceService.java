package com.school.management.api.service;

import com.school.management.api.entity.Attendance;
import com.school.management.api.model.requstModel.AttendanceRequestDto;
import com.school.management.api.model.responseModel.AttendanceResponseDto;
import com.school.management.api.repository.AttendanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AttendanceService {

    @Autowired
    private AttendanceRepository attendanceRepository;

    public List<AttendanceResponseDto> markAttendanceBulk(List<AttendanceRequestDto> requestList) {

        if (requestList.isEmpty()) return List.of();

        List<String> studentIds = requestList.stream()
                .map(AttendanceRequestDto::getStudentId)
                .toList();

        LocalDate date = requestList.get(0).getDate();

        List<Attendance> existingList =
                attendanceRepository.findByStudentIdsAndDate(studentIds, date);

        Set<String> existingStudentIds = existingList.stream()
                .map(Attendance::getStudentId)
                .collect(Collectors.toSet());

        List<Attendance> toSave = new ArrayList<>();

        for (AttendanceRequestDto request : requestList) {

            if (existingStudentIds.contains(request.getStudentId())) {
                throw new RuntimeException("Duplicate attendance for student: " + request.getStudentId()
                );
            }

            Attendance attendance = new Attendance();
            attendance.setStudentId(request.getStudentId());
            attendance.setClassId(request.getClassId());
            attendance.setDate(request.getDate());
            attendance.setStatus(request.getStatus());
            attendance.setCreatedAt(LocalDateTime.now());
            attendance.setUpdatedAt(LocalDateTime.now());

            toSave.add(attendance);
        }

        List<Attendance> saved = attendanceRepository.saveAll(toSave);

        return saved.stream()
                .map(this::mapToResponseDto)
                .toList();

    }

    public List<AttendanceResponseDto> getStudentAttendance(String studentId) {
        List<Attendance> attendanceList = attendanceRepository.findAll().stream()
                .filter(a -> a.getStudentId().equals(studentId))
                .toList();

        return attendanceList.stream()
                .map(this::mapToResponseDto)
                .collect(Collectors.toList());
    }

    public List<AttendanceResponseDto> getClassAttendance(String classId, LocalDate date) {

        List<Attendance> attendanceList = attendanceRepository.findAllByClassIdAndDate(classId, date);
        return attendanceList.stream()
                .map(this::mapToResponseDto)
                .collect(Collectors.toList());

    }

    private AttendanceResponseDto mapToResponseDto(Attendance attendance) {
        AttendanceResponseDto dto = new AttendanceResponseDto();
        dto.setId(attendance.getId());
        dto.setStudentId(attendance.getStudentId());
        dto.setClassId(attendance.getClassId());
        dto.setDate(attendance.getDate());
        dto.setStatus(attendance.getStatus());
        dto.setCreatedAt(attendance.getCreatedAt());
        dto.setUpdatedAt(attendance.getUpdatedAt());
        return dto;
    }
}
