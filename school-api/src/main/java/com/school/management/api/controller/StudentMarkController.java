package com.school.management.api.controller;

import com.school.management.api.constants.Constants;
import com.school.management.api.model.requstModel.StudentMarkRequest;
import com.school.management.api.model.responseModel.StudentMarkResponse;
import com.school.management.api.service.StudentMarkService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(Constants.STUDENT_MARK_ROUTE)
@RequiredArgsConstructor
public class StudentMarkController {

    private final StudentMarkService studentMarkService;

    @PostMapping("/create")
    public StudentMarkResponse createStudentMark(@RequestBody StudentMarkRequest request) {
        return studentMarkService.createStudentMark(request);
    }

    @GetMapping("/exam-subject")
    public List<StudentMarkResponse> getMarksByExamSubject(@RequestParam String examSubjectId) {
        return studentMarkService.getMarksByExamSubject(examSubjectId);
    }

    @GetMapping("/all")
    public List<StudentMarkResponse> getAllStudentMarks() {
        return studentMarkService.getAllStudentMarks();
    }

    @GetMapping("/{studentMarkId}")
    public StudentMarkResponse getStudentMarkById(@PathVariable String studentMarkId) {
        return studentMarkService.getStudentMarkById(studentMarkId);
    }

    @PutMapping("/update/{studentMarkId}")
    public StudentMarkResponse updateStudentMark(@PathVariable String studentMarkId, @RequestBody StudentMarkRequest request) {
        return studentMarkService.updateStudentMark(studentMarkId, request);
    }

    @DeleteMapping("/delete/{studentMarkId}")
    public void deleteStudentMark(@PathVariable String studentMarkId) {
        studentMarkService.deleteStudentMark(studentMarkId);
    }

}