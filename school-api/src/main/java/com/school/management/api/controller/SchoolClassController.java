package com.school.management.api.controller;

import com.school.management.api.constants.Constants;
import com.school.management.api.model.requstModel.SchoolClassRequest;
import com.school.management.api.model.responseModel.SchoolClassResponse;
import com.school.management.api.service.SchoolClassService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(Constants.SCHOOL_CLASS_ROUTE)
public class SchoolClassController {

    @Autowired
    private SchoolClassService schoolClassService;

    @GetMapping("/all")
    public ResponseEntity<List<SchoolClassResponse>> getAllClasses() {
        return ResponseEntity.ok(schoolClassService.getAllClasses());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SchoolClassResponse> getClassById(@PathVariable Long id) {
        return ResponseEntity.ok(schoolClassService.getClassById(id));
    }

    @PostMapping("/create")
    public ResponseEntity<SchoolClassResponse> createClass(
            @RequestBody SchoolClassRequest request) {

        return new ResponseEntity<>(
                schoolClassService.createClass(request),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<SchoolClassResponse> updateClass(
            @PathVariable Long id,
            @RequestBody SchoolClassRequest request) {

        return ResponseEntity.ok(
                schoolClassService.updateClass(id, request)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteClass(@PathVariable Long id) {
        schoolClassService.deleteClass(id);
        return ResponseEntity.ok("Class deleted successfully");
    }
}