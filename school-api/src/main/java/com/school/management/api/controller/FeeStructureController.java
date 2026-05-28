package com.school.management.api.controller;

import com.school.management.api.constants.Constants;
import com.school.management.api.model.requstModel.FeeStructureRequest;
import com.school.management.api.model.responseModel.FeeStructureResponse;
import com.school.management.api.service.FeeStructureService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(Constants.FEE_PAYMENT_STRUCTURE)
@RequiredArgsConstructor
public class FeeStructureController {

    private final FeeStructureService feeStructureService;

    @PostMapping("/admin/create")
    public FeeStructureResponse createFeeStructure(@RequestBody FeeStructureRequest request) {
        return feeStructureService.createFeeStructure(request);
    }

    @GetMapping("/all")
    public List<FeeStructureResponse> getFeeStructures(@RequestParam String academicYearId, @RequestParam String classId) {
        return feeStructureService.getFeeStructures(academicYearId, classId);
    }

    @GetMapping("/{feeStructureId}")
    public FeeStructureResponse getFeeStructureById(@PathVariable String feeStructureId) {
        return feeStructureService.getFeeStructureById(feeStructureId);
    }

    @PutMapping("/admin/update/{feeStructureId}")
    public FeeStructureResponse updateFeeStructure(@PathVariable String feeStructureId, @RequestBody FeeStructureRequest request) {
        return feeStructureService.updateFeeStructure(feeStructureId, request);
    }

    @DeleteMapping("/admin/delete/{feeStructureId}")
    public void deleteFeeStructure(@PathVariable String feeStructureId) {
        feeStructureService.deleteFeeStructure(feeStructureId);
    }

}