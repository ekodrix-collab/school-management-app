package com.school.management.api.controller;

import com.school.management.api.constants.Constants;
import com.school.management.api.model.requstModel.AdmissionRequest;
import com.school.management.api.model.requstModel.UserRequestDto;
import com.school.management.api.model.responseModel.AdmissionResponse;
import com.school.management.api.model.responseModel.UserResponse;
import com.school.management.api.service.AdmissionService;
import com.school.management.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(Constants.ADMIN_ROUTE)
public class AdmissionController {

    @Autowired
    AdmissionService admissionService;

    @Autowired
    UserService userService;

    @PostMapping("/admission")
    public AdmissionResponse createAdmission(@RequestBody AdmissionRequest request){
       return admissionService.createAdmission(request);
    }

    @PostMapping("/create")
    public UserResponse createUser(@RequestBody UserRequestDto request) {
        return userService.createUser(request);
    }

    @GetMapping("/admission/all")
    public List<AdmissionResponse> getAllAdmissions() {
        return admissionService.getAllAdmissions();
    }

    @GetMapping("/admission/{admissionId}")
    public AdmissionResponse getAdmissionById(@PathVariable String admissionId) {
        return admissionService.getAdmissionById(admissionId);
    }

    @PutMapping("/admission/{admissionId}")
    public AdmissionResponse updateAdmission(@PathVariable String admissionId, @RequestBody AdmissionRequest request) {
        return admissionService.updateAdmission(admissionId, request);
    }

    @DeleteMapping("/admission/{admissionId}")
    public String deleteAdmission(@PathVariable String admissionId) {
        return admissionService.deleteAdmission(admissionId);
    }

    @GetMapping("/admission/total")
    public long getTotalAdmissionsByAcademicYear(@RequestParam String academicYearId) {
        return admissionService.getTotalAdmissionsByAcademicYear(academicYearId);
    }

    @GetMapping("/admission/filter-by-academic-year")
    public List<AdmissionResponse> getAdmissionsByAcademicYear(@RequestParam String academicYearId) {
        return admissionService.getAdmissionsByAcademicYear(academicYearId);
    }
}
