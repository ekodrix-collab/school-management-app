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

    @PostMapping("/create")
    public StudentResponseDto createStudent(@RequestBody StudentRequestDto requestDto){
        return studentService.createStudent(requestDto);
    }

//    @PutMapping("/{id}")
//    public StudentResponseDto updateStudent(@PathVariable Long id,@RequestBody StudentRequestDto requestDto){
//        return studentService.updateStudent(id,requestDto);
//    }
//
//    @DeleteMapping("/{id}")
//    public String deleteStudent(@PathVariable Long id){
//        return studentService.deleteStudent(id);
//    }
//
//    @GetMapping("/all")
//    public List<StudentResponseDto>getAllStudent(){
//        return studentService.getAllStudents();
//    }
//
//    @GetMapping("/{id}")
//    public StudentResponseDto getStudentById(@PathVariable Long id) {
//        return studentService.getStudentById(id);
//    }

}
