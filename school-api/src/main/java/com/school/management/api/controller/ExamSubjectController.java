package com.school.management.api.controller;

import com.school.management.api.model.requstModel.ExamSubjectRequest;
import com.school.management.api.model.responseModel.ExamSubjectResponse;
import com.school.management.api.service.ExamSubjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/exam-subjects")
@RequiredArgsConstructor
public class ExamSubjectController {

    private final ExamSubjectService examSubjectService;

    @PostMapping("/create")
    public ExamSubjectResponse createExamSubject(@RequestBody ExamSubjectRequest request) {
        return examSubjectService.createExamSubject(request);
    }

    @GetMapping
    public List<ExamSubjectResponse> getExamSubjects(@RequestParam String examId, @RequestParam String classId) {
        return examSubjectService.getExamSubjects(examId, classId);
    }

}