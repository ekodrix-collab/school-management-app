package com.school.management.api.controller;

import com.school.management.api.constants.Constants;
import com.school.management.api.model.requstModel.AttendanceRequest;
import com.school.management.api.model.responseModel.AttendanceDetailsResponse;
import com.school.management.api.model.responseModel.AttendanceResponse;
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
    AttendanceService attendanceService;

    @PostMapping("/create")
    public AttendanceResponse createAttendance(@RequestBody AttendanceRequest request) {
        return attendanceService.createAttendance(request);
    }

    @GetMapping("/all")
    public List<AttendanceDetailsResponse> getAllAttendance() {
        return attendanceService.getAllAttendance();
    }

    @GetMapping("/{attendanceSessionId}")
    public AttendanceDetailsResponse getAttendanceById(@PathVariable String attendanceSessionId) {
        return attendanceService.getAttendanceById(attendanceSessionId);
    }

    @PutMapping("/{attendanceSessionId}")
    public AttendanceResponse updateAttendance(@PathVariable String attendanceSessionId, @RequestBody AttendanceRequest request) {
        return attendanceService.updateAttendance(attendanceSessionId, request);
    }

    @DeleteMapping("/{attendanceSessionId}")
    public String deleteAttendance(@PathVariable String attendanceSessionId) {
        return attendanceService.deleteAttendance(attendanceSessionId);
    }

    @GetMapping("/filter")
    public List<AttendanceDetailsResponse> getAttendanceWithFilters(
            @RequestParam(required = false) String classId,
            @RequestParam(required = false) String academicYearId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate attendanceDate) {
        return attendanceService.getAttendanceWithFilters(classId, academicYearId, attendanceDate);
    }
}
