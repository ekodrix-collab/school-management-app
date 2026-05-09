package com.school.management.api.controller;

import com.school.management.api.constants.Constants;
import com.school.management.api.model.requstModel.OnboardRequest;
import com.school.management.api.model.responseModel.OnBoardResponse;
import com.school.management.api.model.responseModel.TeacherResponseDto;
import com.school.management.api.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = Constants.TEACHER_ROUTE)
public class TeacherController {

    @Autowired
    TeacherService teacherService;

    @PostMapping("/on-board")
    public OnBoardResponse onBoardTeacher(@RequestBody OnboardRequest request){
        return teacherService.createTeacher(request);
    }

    @GetMapping("/all")
    public ResponseEntity<List<TeacherResponseDto>> getAllTeachers(){
        List<TeacherResponseDto> response = teacherService.getAllTeachers();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{teacherId}")
    public ResponseEntity<TeacherResponseDto> getTeacherById(@PathVariable UUID teacherId){
        TeacherResponseDto response = teacherService.getTeacherByTeacherId(teacherId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/{teacherId}")
    public ResponseEntity<TeacherResponseDto> updateTeacher(@PathVariable UUID teacherId, @RequestBody OnboardRequest request){
        TeacherResponseDto response = teacherService.updateTeacher(teacherId, request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{teacherId}")
    public ResponseEntity<String> deleteTeacher(@PathVariable UUID teacherId){
        String response = teacherService.deleteTeacher(teacherId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
