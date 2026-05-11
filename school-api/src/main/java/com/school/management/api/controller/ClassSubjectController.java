package com.school.management.api.controller;

import com.school.management.api.constants.Constants;
import com.school.management.api.entity.ClassSubject;
import com.school.management.api.model.requstModel.ClassSubjectRequest;
import com.school.management.api.model.responseModel.ClassSubjectResponse;
import com.school.management.api.service.ClassSubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(Constants.SUBJECT_CLASS_ROUTE)
public class ClassSubjectController {

    @Autowired
    ClassSubjectService classSubjectService;

    @PostMapping("/create")
    public ClassSubjectResponse createClassSubject(@RequestBody ClassSubjectRequest request){
        return classSubjectService.createClassSubject(request);
    }

    @GetMapping("/all")
    public List<ClassSubjectResponse> getAllClassSubject(){
        return classSubjectService.getAllClassSubject();
    }

    //get by id
    //edit
    //delete

}
