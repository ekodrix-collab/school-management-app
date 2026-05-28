package com.school.management.api.controller;

import com.school.management.api.constants.Constants;
import com.school.management.api.model.requstModel.TeacherClassSubjectsRequest;
import com.school.management.api.model.responseModel.TeacherClassSubjectsResponse;
import com.school.management.api.service.TeacherClassSubjectsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(Constants.TEACHER_SUBJECT_CLASS_ROUTE)
public class TeacherClassSubjectsController {

    @Autowired
    TeacherClassSubjectsService teacherClassSubjectsService;

    @PostMapping("/admin/create")
    public TeacherClassSubjectsResponse createTeacherClassSubjects(@RequestBody TeacherClassSubjectsRequest request){
        return teacherClassSubjectsService.createTeacherClassSubjects(request);
    }

    @PutMapping("/admin/update/{teacherClassSubjectId}")
    public TeacherClassSubjectsResponse updateTeacherClassSubjects(@PathVariable String teacherClassSubjectId, @RequestBody TeacherClassSubjectsRequest request) {
        return teacherClassSubjectsService.updateTeacherClassSubjects(teacherClassSubjectId, request);
    }

    @GetMapping("/{teacherClassSubjectId}")
    public TeacherClassSubjectsResponse getTeacherClassSubjectById(@PathVariable String teacherClassSubjectId) {
        return teacherClassSubjectsService.getTeacherClassSubjectById(teacherClassSubjectId);
    }

    @GetMapping("/all")
    public List<TeacherClassSubjectsResponse> getAllTeacherClassSubjects() {
        return teacherClassSubjectsService.getAllTeacherClassSubjects();
    }

    @DeleteMapping("/admin/delete/{teacherClassSubjectId}")
    public String deleteTeacherClassSubjects(@PathVariable String teacherClassSubjectId) {
        return teacherClassSubjectsService.deleteTeacherClassSubjects(teacherClassSubjectId);
    }

}
