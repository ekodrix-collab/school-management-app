package com.school.management.api.controller;

import com.school.management.api.constants.Constants;
import com.school.management.api.model.requstModel.FeePaymentRequest;
import com.school.management.api.model.responseModel.FeePaymentResponse;
import com.school.management.api.service.FeePaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(Constants.FEE_PAYMENT)
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

    @GetMapping("/all")
    public List<FeePaymentResponse> getAllPayments() {
        return feePaymentService.getAllPayments();
    }

    @PutMapping("/update/{paymentId}")
    public FeePaymentResponse updateFeePayment(@PathVariable String paymentId, @RequestBody FeePaymentRequest request) {
        return feePaymentService.updateFeePayment(paymentId, request);
    }

    @DeleteMapping("/delete/{paymentId}")
    public void deleteFeePayment(@PathVariable String paymentId) {
        feePaymentService.deleteFeePayment(paymentId);
    }

}