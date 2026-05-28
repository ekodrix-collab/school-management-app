package com.school.management.api.controller;

import com.school.management.api.constants.Constants;
import com.school.management.api.model.requstModel.StudentRequestDto;
import com.school.management.api.model.responseModel.StudentResponseDto;
import com.school.management.api.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping(value = Constants.STUDENT_ROUTE)
public class StudentController {

    @Autowired
    StudentService studentService;

    @PostMapping("/admin/create")
    public StudentResponseDto createStudent(@RequestBody StudentRequestDto requestDto){
        return studentService.createStudent(requestDto);
    }

    @GetMapping("/all")
    public List<StudentResponseDto> getAllStudents() {
        return studentService.getAllStudents();
    }

    @GetMapping("/{studentId}")
    public StudentResponseDto getStudentById(@PathVariable String studentId) {
        return studentService.getStudentById(studentId);
    }

    @PutMapping("/admin/update/{studentId}")
    public StudentResponseDto updateStudent(@PathVariable String studentId, @RequestBody StudentRequestDto request) {
        return studentService.updateStudent(studentId, request);
    }

    @DeleteMapping("/admin/delete/{studentId}")
    public void deleteStudent(@PathVariable String studentId) {
        studentService.deleteStudent(studentId);
    }

}
