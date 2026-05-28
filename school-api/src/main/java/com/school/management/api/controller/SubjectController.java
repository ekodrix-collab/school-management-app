package com.school.management.api.controller;

import com.school.management.api.constants.Constants;
import com.school.management.api.model.requstModel.SubjectRequestDto;
import com.school.management.api.model.responseModel.SubjectResponseDto;
import com.school.management.api.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(Constants.SUBJECT_ROUTE)
public class SubjectController {

    @Autowired
    private SubjectService subjectService;

    @PostMapping("/admin/create")
    public SubjectResponseDto createSubject(@RequestBody SubjectRequestDto requestDto) {
        return subjectService.createSubject(requestDto);
    }

    @PutMapping("/admin/{subjectId}")
    public SubjectResponseDto updateSubject(@PathVariable String subjectId, @RequestBody SubjectRequestDto requestDto) {
        return subjectService.updateSubject(subjectId, requestDto);
    }

    @DeleteMapping("/admin/{subjectId}")
    public String deleteSubject(@PathVariable String subjectId) {
        return subjectService.deleteSubject(subjectId);
    }

    @GetMapping("/all")
    public List<SubjectResponseDto> getAllSubjects() {
        return subjectService.getAllSubjects();
    }

    @GetMapping("/{subjectId}")
    public SubjectResponseDto getSubjectById(@PathVariable String subjectId) {
        return subjectService.getSubjectById(subjectId);
    }

}
