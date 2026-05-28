package com.school.management.api.controller;

import com.school.management.api.constants.Constants;
import com.school.management.api.model.requstModel.SchoolRequestDto;
import com.school.management.api.model.responseModel.SchoolResponse;
import com.school.management.api.service.SchoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping(Constants.SCHOOL_ROUTE)
public class SchoolController {

    @Autowired
    SchoolService schoolService;

    @PostMapping("/create")
    public SchoolResponse createSchool(@RequestBody SchoolRequestDto request) {
        return schoolService.createSchool(request);
    }

    @GetMapping("/all")
    public List<SchoolResponse> getAllSchools() {
        return schoolService.getAllSchools();
    }

    @GetMapping("/{schoolId}")
    public SchoolResponse getSchoolById(@PathVariable String schoolId) {
        return schoolService.getSchoolById(schoolId);
    }

    @PutMapping("/update/{schoolId}")
    public SchoolResponse updateSchool(@PathVariable String schoolId, @RequestBody SchoolRequestDto request) {
        return schoolService.updateSchool(schoolId, request);
    }

    @DeleteMapping("/delete/{schoolId}")
    public void deleteSchool(@PathVariable String schoolId) {
        schoolService.deleteSchool(schoolId);
    }

}
