package com.school.management.api.controller;

import com.school.management.api.constants.Constants;
import com.school.management.api.model.requstModel.StudentRequestDto;
import com.school.management.api.model.responseModel.StudentResponseDto;
import com.school.management.api.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value = Constants.STUDENT_ROUTE)
public class StudentController {

    @Autowired
    StudentService studentService;

    @PostMapping("/create")
    public StudentResponseDto createStudent(@RequestBody StudentRequestDto requestDto){
        return studentService.createStudent(requestDto);
    }

    //edit
    //get all
    //get by id
    //delete

}
