package com.school.management.api.controller;

import com.school.management.api.model.requstModel.StudentMarkRequest;
import com.school.management.api.model.responseModel.StudentMarkResponse;
import com.school.management.api.service.StudentMarkService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/student-marks")
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

}