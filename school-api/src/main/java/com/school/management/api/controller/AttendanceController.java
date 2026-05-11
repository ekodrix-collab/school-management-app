package com.school.management.api.controller;

import com.school.management.api.constants.Constants;
import com.school.management.api.model.requstModel.AttendanceRequest;
import com.school.management.api.model.responseModel.AttendanceResponse;
import com.school.management.api.service.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(Constants.ATTENDANCE)
public class AttendanceController {

    @Autowired
    AttendanceService attendanceService;

    @PostMapping("/create")
    public AttendanceResponse createAttendance(@RequestBody AttendanceRequest request) {
        return attendanceService.createAttendance(request);
    }

    //edit
    //get
    //get by id
    //get with filters

}
