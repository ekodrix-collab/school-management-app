package com.school.management.api.controller;

import com.school.management.api.constants.Constants;
import com.school.management.api.model.requstModel.ExamRequestDto;
import com.school.management.api.model.responseModel.ExamResponseDto;
import com.school.management.api.service.ExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(Constants.EXAM_ROUTE)
public class ExamController {

    @Autowired
    private ExamService examService;

    @PostMapping("/create")
    public ExamResponseDto createExam(@RequestBody ExamRequestDto requestDto) {
        return examService.createExam(requestDto);
    }

    @PutMapping("/{examId}")
    public ExamResponseDto updateExam(@PathVariable String examId, @RequestBody ExamRequestDto requestDto) {
        return examService.updateExam(examId, requestDto);
    }

    @DeleteMapping("/{examId}")
    public String deleteExam(@PathVariable String examId) {
        return examService.deleteExam(examId);
    }

    @GetMapping("/all")
    public List<ExamResponseDto> getAllExams() {
        return examService.getAllExams();
    }

    @GetMapping("/{examId}")
    public ExamResponseDto getExamById(@PathVariable String examId) {
        return examService.getExamById(examId);
    }

}
