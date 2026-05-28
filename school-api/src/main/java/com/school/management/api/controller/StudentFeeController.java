package com.school.management.api.controller;

import com.school.management.api.constants.Constants;
import com.school.management.api.model.requstModel.StudentFeeRequest;
import com.school.management.api.model.responseModel.StudentFeeResponse;
import com.school.management.api.service.StudentFeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(Constants.STUDENT_FEE)
@RequiredArgsConstructor
public class StudentFeeController {

    private final StudentFeeService studentFeeService;

    @PostMapping("/create")
    public StudentFeeResponse createStudentFee(@RequestBody StudentFeeRequest request) {
        return studentFeeService.createStudentFee(request);
    }

    @GetMapping("/{studentId}")
    public List<StudentFeeResponse> getStudentFees(@PathVariable String studentId) {
        return studentFeeService.getStudentFees(studentId);
    }

    @GetMapping("/all")
    public List<StudentFeeResponse> getAllStudentFees() {
        return studentFeeService.getAllStudentFees();
    }

    @PutMapping("/update/{studentFeeId}")
    public StudentFeeResponse updateStudentFee(@PathVariable String studentFeeId, @RequestBody StudentFeeRequest request) {
        return studentFeeService.updateStudentFee(studentFeeId, request);
    }

    @DeleteMapping("/delete/{studentFeeId}")
    public void deleteStudentFee(@PathVariable String studentFeeId) {
        studentFeeService.deleteStudentFee(studentFeeId);
    }

}