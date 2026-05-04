package com.school.management.api.controller;

import com.school.management.api.constants.Constants;
import com.school.management.api.model.requstModel.AttendanceRequestDto;
import com.school.management.api.model.responseModel.AttendanceResponseDto;
import com.school.management.api.service.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(Constants.ATTENDANCE)
public class AttendanceController {

    @Autowired
    private AttendanceService attendanceService;

    @PostMapping("/add")
    public List<AttendanceResponseDto> markAttendance(@RequestBody List<AttendanceRequestDto> requestList) {
        return attendanceService.markAttendanceBulk(requestList);
    }

    @GetMapping("/student/{studentId}")
    public List<AttendanceResponseDto> getStudentAttendance(@PathVariable String studentId) {
        return attendanceService.getStudentAttendance(studentId);
    }

    @GetMapping("/class/{classId}")
    public List<AttendanceResponseDto> getClassAttendance(@PathVariable String classId,
            @RequestParam(value = "date", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {

        LocalDate searchDate = (date != null) ? date : LocalDate.now();
        return attendanceService.getClassAttendance(classId, searchDate);
    }

}
