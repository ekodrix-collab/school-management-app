package com.school.management.api.controller;

import com.school.management.api.constants.Constants;
import com.school.management.api.model.requstModel.OnboardRequest;
import com.school.management.api.model.responseModel.OnBoardResponse;
import com.school.management.api.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = Constants.TEACHER_ROUTE)
public class TeacherController {

    @Autowired
    TeacherService teacherService;

    @PostMapping("/on-board")
    public ResponseEntity<OnBoardResponse> onBoardTeacher(@RequestBody OnboardRequest request){
        OnBoardResponse response = teacherService.createTeacher(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

}
