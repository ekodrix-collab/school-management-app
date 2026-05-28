package com.school.management.api.controller;

import com.school.management.api.constants.Constants;
import com.school.management.api.model.requstModel.ExamSubjectRequest;
import com.school.management.api.model.responseModel.ExamSubjectResponse;
import com.school.management.api.service.ExamSubjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(Constants.EXAM_SUBJECT_ROUTE)
@RequiredArgsConstructor
public class ExamSubjectController {

    private final ExamSubjectService examSubjectService;

    @PostMapping("/create")
    public ExamSubjectResponse createExamSubject(@RequestBody ExamSubjectRequest request) {
        return examSubjectService.createExamSubject(request);
    }

    @GetMapping("/all")
    public List<ExamSubjectResponse> getExamSubjects(@RequestParam String examId, @RequestParam String classId) {
        return examSubjectService.getExamSubjects(examId, classId);
    }

    @PutMapping("/{examSubjectId}")
    public ExamSubjectResponse updateExamSubject(@PathVariable String examSubjectId, @RequestBody ExamSubjectRequest request) {
        return examSubjectService.updateExamSubject(examSubjectId, request);
    }

    @GetMapping("/{examSubjectId}")
    public ExamSubjectResponse getExamSubjectById(@PathVariable String examSubjectId) {
        return examSubjectService.getExamSubjectById(examSubjectId);
    }

    @DeleteMapping("/{examSubjectId}")
    public String deleteExamSubject(@PathVariable String examSubjectId) {
        return examSubjectService.deleteExamSubject(examSubjectId);
    }

}