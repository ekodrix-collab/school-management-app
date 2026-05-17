package com.school.management.api.controller;

import com.school.management.api.model.requstModel.FeeStructureRequest;
import com.school.management.api.model.responseModel.FeeStructureResponse;
import com.school.management.api.service.FeeStructureService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/fee-structures")
@RequiredArgsConstructor
public class FeeStructureController {

    private final FeeStructureService feeStructureService;

    @PostMapping("/create")
    public FeeStructureResponse createFeeStructure(@RequestBody FeeStructureRequest request) {
        return feeStructureService.createFeeStructure(request);
    }

    @GetMapping
    public List<FeeStructureResponse> getFeeStructures(@RequestParam String academicYearId, @RequestParam String classId) {
        return feeStructureService.getFeeStructures(academicYearId, classId);
    }

}