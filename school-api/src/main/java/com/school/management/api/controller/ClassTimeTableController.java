package com.school.management.api.controller;

import com.school.management.api.constants.Constants;
import com.school.management.api.model.requstModel.ClassTimetableRequest;
import com.school.management.api.model.responseModel.ClassTimetableResponse;
import com.school.management.api.service.ClassTimeTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(Constants.CLASS_TIME_TABLE)
public class ClassTimeTableController {

    @Autowired
    ClassTimeTableService classTimetableService;

    @PostMapping("/admin/create")
    public ClassTimetableResponse createTimetable(@RequestBody ClassTimetableRequest request) {
        return classTimetableService.createTimetable(request);
    }

    @GetMapping("/all")
    public List<ClassTimetableResponse> getClassTimetable(@RequestParam String schoolId, @RequestParam String academicYearId, @RequestParam String classId, @RequestParam String dayName) {
        return classTimetableService.getClassTimetable(schoolId, academicYearId, classId, dayName);
    }

    @PutMapping("/admin/{timetableId}")
    public ClassTimetableResponse updateTimetable(@PathVariable String timetableId, @RequestBody ClassTimetableRequest request) {
        return classTimetableService.updateTimetable(timetableId, request);
    }

    @GetMapping("/{timetableId}")
    public ClassTimetableResponse getTimetableById(@PathVariable String timetableId) {
        return classTimetableService.getTimetableById(timetableId);
    }

    @DeleteMapping("/admin/{timetableId}")
    public ClassTimetableResponse deleteTimetable(@PathVariable String timetableId) {
        return classTimetableService.deleteTimetable(timetableId);
    }

}
