package com.school.management.api.controller;

import com.school.management.api.model.requstModel.FeePaymentRequest;
import com.school.management.api.model.responseModel.FeePaymentResponse;
import com.school.management.api.service.FeePaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/fee-payments")
@RequiredArgsConstructor
public class FeePaymentController {

    private final FeePaymentService feePaymentService;

    @PostMapping("/create")
    public FeePaymentResponse createFeePayment(@RequestBody FeePaymentRequest request) {
        return feePaymentService.createFeePayment(request);
    }

    @GetMapping("/{studentFeeId}")
    public List<FeePaymentResponse> getPayments(@PathVariable String studentFeeId) {
        return feePaymentService.getPayments(studentFeeId);
    }

}