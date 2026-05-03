package com.school.management.api.controller;

import com.school.management.api.model.requstModel.StudentRequestDto;
import com.school.management.api.model.responseModel.StudentResponseDto;
import com.school.management.api.service.AdmissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {

    @Autowired
    AdmissionService admissionService;

    @PostMapping("/admission")
    public StudentResponseDto createAdmission(@RequestBody StudentRequestDto request){
       return admissionService.createAdmission(request);
    }

}
