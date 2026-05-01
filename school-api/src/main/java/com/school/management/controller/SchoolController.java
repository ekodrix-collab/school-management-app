package com.school.management.controller;

import com.school.management.dto.ApiResponse;
import com.school.management.dto.SchoolDto;
import com.school.management.service.SchoolService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/schools")
@RequiredArgsConstructor
public class SchoolController {

    private final SchoolService schoolService;

    @PostMapping
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<ApiResponse<SchoolDto>> createSchool(@RequestBody SchoolDto schoolDto) {
        return ResponseEntity.ok(ApiResponse.success("School created successfully", schoolService.createSchool(schoolDto)));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<ApiResponse<SchoolDto>> updateSchool(@PathVariable Long id, @RequestBody SchoolDto schoolDto) {
        return ResponseEntity.ok(ApiResponse.success("School updated successfully", schoolService.updateSchool(id, schoolDto)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<SchoolDto>> getSchool(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success("School fetched successfully", schoolService.getSchoolById(id)));
    }

    @GetMapping
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<ApiResponse<List<SchoolDto>>> getAllSchools() {
        return ResponseEntity.ok(ApiResponse.success("Schools fetched successfully", schoolService.getAllSchools()));
    }

    @PatchMapping("/{id}/status")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<ApiResponse<Void>> changeStatus(@PathVariable Long id, @RequestParam boolean active) {
        schoolService.activateDeactivateSchool(id, active);
        return ResponseEntity.ok(ApiResponse.success("School status updated successfully", null));
    }
}
