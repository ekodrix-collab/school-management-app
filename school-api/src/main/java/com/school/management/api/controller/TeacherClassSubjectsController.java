package com.school.management.api.controller;

import com.school.management.api.constants.Constants;
import com.school.management.api.model.requstModel.TeacherClassSubjectsRequest;
import com.school.management.api.model.responseModel.TeacherClassSubjectsResponse;
import com.school.management.api.service.TeacherClassSubjectsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(Constants.TEACHER_SUBJECT_CLASS_ROUTE)
public class TeacherClassSubjectsController {

    @Autowired
    TeacherClassSubjectsService teacherClassSubjectsService;

    @PostMapping("/create")
    public TeacherClassSubjectsResponse createTeacherClassSubjects(@RequestBody TeacherClassSubjectsRequest request){
        return teacherClassSubjectsService.createTeacherClassSubjects(request);
    }


}
