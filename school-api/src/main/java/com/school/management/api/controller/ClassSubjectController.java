package com.school.management.api.controller;

import com.school.management.api.constants.Constants;
import com.school.management.api.model.requstModel.ClassSubjectRequest;
import com.school.management.api.model.responseModel.ClassSubjectResponse;
import com.school.management.api.service.ClassSubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(Constants.CLASS_SUBJECT_ROUTE)
public class ClassSubjectController {

    @Autowired
    ClassSubjectService classSubjectService;

    @PostMapping("/admin/create")
    public ClassSubjectResponse createClassSubject(@RequestBody ClassSubjectRequest request){
        return classSubjectService.createClassSubject(request);
    }

    @GetMapping("/all")
    public List<ClassSubjectResponse> getAllClassSubject(){
        return classSubjectService.getAllClassSubject();
    }

    @GetMapping("/{classSubjectId}")
    public ClassSubjectResponse getClassSubjectById(@PathVariable String classSubjectId) {
        return classSubjectService.getClassSubjectResponseById(classSubjectId);
    }

    @PutMapping("/admin/{classSubjectId}")
    public ClassSubjectResponse updateClassSubject(@PathVariable String classSubjectId, @RequestBody ClassSubjectRequest request) {
        return classSubjectService.updateClassSubject(classSubjectId, request);
    }

    @DeleteMapping("/admin/{classSubjectId}")
    public String deleteClassSubject(@PathVariable String classSubjectId) {
        return classSubjectService.deleteClassSubject(classSubjectId);
    }

}
