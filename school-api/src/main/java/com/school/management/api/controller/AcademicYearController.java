package com.school.management.api.controller;

import com.school.management.api.constants.Constants;
import com.school.management.api.model.requstModel.AcademicYearRequestDto;
import com.school.management.api.model.responseModel.AcademicYearResponseDto;
import com.school.management.api.service.AcademicYearService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = Constants.ACADEMIC_YEAR_ROUTE)
@RequiredArgsConstructor
public class AcademicYearController {

    private final AcademicYearService academicYearService;

    @PostMapping("/create")
    public ResponseEntity<AcademicYearResponseDto> createAcademicYear(@RequestBody AcademicYearRequestDto requestDto) {
        AcademicYearResponseDto response = academicYearService.createAcademicYear(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/all")
    public ResponseEntity<List<AcademicYearResponseDto>> getAllAcademicYears() {
        List<AcademicYearResponseDto> response = academicYearService.getAllAcademicYears();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{academicYearId}")
    public ResponseEntity<AcademicYearResponseDto> getAcademicYearById(@PathVariable String academicYearId) {
        AcademicYearResponseDto response = academicYearService.getAcademicYearById(academicYearId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/{academicYearId}")
    public ResponseEntity<AcademicYearResponseDto> updateAcademicYear(@PathVariable String academicYearId,
            @RequestBody AcademicYearRequestDto requestDto) {
        AcademicYearResponseDto response = academicYearService.updateAcademicYear(academicYearId, requestDto);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{academicYearId}")
    public ResponseEntity<String> deleteAcademicYear(@PathVariable String academicYearId) {
        String response = academicYearService.deleteAcademicYear(academicYearId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
