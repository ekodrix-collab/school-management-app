package com.school.management.api.controller;

import com.school.management.api.constants.Constants;
import com.school.management.api.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = Constants.STUDENT_ROUTE)
public class StudentController {

    @Autowired
    StudentService studentService;
}
