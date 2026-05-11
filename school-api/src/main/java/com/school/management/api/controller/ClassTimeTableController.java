package com.school.management.api.controller;

import com.school.management.api.model.requstModel.ClassTimetableRequest;
import com.school.management.api.model.responseModel.ClassTimetableResponse;
import com.school.management.api.service.ClassTimeTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/time-table")
public class ClassTimeTableController {

    @Autowired
    ClassTimeTableService classTimetableService;

    @PostMapping("/creating")
    public ClassTimetableResponse createTimetable(@RequestBody ClassTimetableRequest request) {
        return classTimetableService.createTimetable(request);
    }

    @GetMapping("/all")
    public List<ClassTimetableResponse> getClassTimetable(@RequestParam String schoolId, @RequestParam String academicYearId, @RequestParam String classId, @RequestParam String dayName) {
        return classTimetableService.getClassTimetable(schoolId, academicYearId, classId, dayName);
    }

    //edit
    //get by id
    //delete

}
