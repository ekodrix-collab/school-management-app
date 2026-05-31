package com.school.management.api.controller.authController;

import com.school.management.api.constants.Constants;
import com.school.management.api.model.requstModel.LoginRequest;
import com.school.management.api.model.requstModel.RegisterRequest;
import com.school.management.api.model.responseModel.AuthResponse;
import com.school.management.api.model.responseModel.MessageResponse;
import com.school.management.api.model.responseModel.VerifyOtpResponse;
import com.school.management.api.model.requstModel.ForgotPasswordEmailRequest;
import com.school.management.api.model.requstModel.ResetPasswordEmailRequest;
import com.school.management.api.model.requstModel.VerifyOtpRequest;
import com.school.management.api.service.authService.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = Constants.AUTH_ROUTE)
public class AuthController {

    @Autowired
    AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/forgot-password/email")
    public ResponseEntity<MessageResponse> forgotPasswordEmail(@RequestBody ForgotPasswordEmailRequest request) {
        return ResponseEntity.ok(authService.sendEmailOtp(request));
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<VerifyOtpResponse> verifyOtp(@RequestBody VerifyOtpRequest request) {
        return ResponseEntity.ok(authService.verifyEmailOtp(request));
    }

    @PostMapping("/reset-password/email")
    public ResponseEntity<MessageResponse> resetPasswordEmail(@RequestBody ResetPasswordEmailRequest request) {
        return ResponseEntity.ok(authService.resetPasswordWithEmailOtp(request));
    }

    @PostMapping("/logout")
    public ResponseEntity<MessageResponse> logout(@RequestHeader(value = "Authorization", required = false) String bearerHeader) {
        return ResponseEntity.ok(authService.logout(bearerHeader));
    }
}