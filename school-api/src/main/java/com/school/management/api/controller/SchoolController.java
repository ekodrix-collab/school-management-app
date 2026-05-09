package com.school.management.api.controller;

import com.school.management.api.constants.Constants;
import com.school.management.api.model.requstModel.SchoolRequestDto;
import com.school.management.api.model.responseModel.SchoolResponse;
import com.school.management.api.service.SchoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(Constants.SCHOOL_ROUTE)
public class SchoolController {

    @Autowired
    SchoolService schoolService;

    @PostMapping("/create")
    public SchoolResponse createSchool(@RequestBody SchoolRequestDto request) {
        return schoolService.createSchool(request);
    }

    //edit
    //delete
    //get all filter by academic year

}
