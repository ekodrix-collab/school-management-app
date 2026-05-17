package com.school.management.api.controller;

import com.school.management.api.constants.Constants;
import com.school.management.api.model.requstModel.ExamRequest;
import com.school.management.api.model.responseModel.ExamResponse;
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
    public ExamResponse createExam(@RequestBody ExamRequest request) {
        return examService.createExam(request);
    }

    @GetMapping
    public List<ExamResponse> getAllExams(@RequestParam String schoolId, @RequestParam String academicYearId) {
        return examService.getAllExams(schoolId, academicYearId);

    }

    //edit
    //get by id
    //delete

}
