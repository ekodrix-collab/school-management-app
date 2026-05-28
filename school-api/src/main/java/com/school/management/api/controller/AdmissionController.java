package com.school.management.api.controller;

import com.school.management.api.constants.Constants;
import com.school.management.api.model.requstModel.AdmissionRequest;
import com.school.management.api.model.responseModel.AdmissionResponse;
import com.school.management.api.service.AdmissionService;
import com.school.management.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(Constants.ADMISSION_ROUTE)
public class AdmissionController {

    @Autowired
    AdmissionService admissionService;

    @Autowired
    UserService userService;

    @PostMapping("/admin/create")
    public AdmissionResponse createAdmission(@RequestBody AdmissionRequest request){
       return admissionService.createAdmission(request);
    }

    @GetMapping("/all")
    public List<AdmissionResponse> getAllAdmissions() {
        return admissionService.getAllAdmissions();
    }

    @GetMapping("/{admissionId}")
    public AdmissionResponse getAdmissionById(@PathVariable String admissionId) {
        return admissionService.getAdmissionById(admissionId);
    }

    @PutMapping("/admin/{admissionId}")
    public AdmissionResponse updateAdmission(@PathVariable String admissionId, @RequestBody AdmissionRequest request) {
        return admissionService.updateAdmission(admissionId, request);
    }

    @DeleteMapping("/admin/{admissionId}")
    public String deleteAdmission(@PathVariable String admissionId) {
        return admissionService.deleteAdmission(admissionId);
    }

    @GetMapping("/total")
    public long getTotalAdmissionsByAcademicYear(@RequestParam String academicYearId) {
        return admissionService.getTotalAdmissionsByAcademicYear(academicYearId);
    }

    @GetMapping("/admin/filter-by-academic-year")
    public List<AdmissionResponse> getAdmissionsByAcademicYear(@RequestParam String academicYearId) {
        return admissionService.getAdmissionsByAcademicYear(academicYearId);
    }

}
