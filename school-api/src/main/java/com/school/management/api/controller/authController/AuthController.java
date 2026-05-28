package com.school.management.api.controller.authController;


import com.school.management.api.constants.Constants;
import com.school.management.api.model.requstModel.ForgotPasswordRequest;
import com.school.management.api.model.requstModel.LoginRequest;
import com.school.management.api.model.requstModel.RegisterRequest;
import com.school.management.api.model.requstModel.ResetPasswordRequest;
import com.school.management.api.model.responseModel.AuthResponse;
import com.school.management.api.model.responseModel.ForgotPasswordResponse;
import com.school.management.api.model.responseModel.MessageResponse;
import com.school.management.api.service.authService.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = Constants.AUTH_ROUTE)
public class AuthController {

    @Autowired
    AuthService authService;

    // ─────────────────────────────────────────────────────────────
    //  Existing endpoints (unchanged)
    // ─────────────────────────────────────────────────────────────

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    // ─────────────────────────────────────────────────────────────
    //  New endpoints
    // ─────────────────────────────────────────────────────────────

    /**
     * Step 1 — Request a password reset token.
     * The client supplies the registered mobile number.
     * A short-lived token is returned (in production: deliver via SMS/email).
     *
     * POST /api/v1/auth/forgot-password
     */
    @PostMapping("/forgot-password")
    public ResponseEntity<ForgotPasswordResponse> forgotPassword(
            @RequestBody ForgotPasswordRequest request) {
        return ResponseEntity.ok(authService.forgotPassword(request));
    }

    /**
     * Step 2 — Submit the reset token and choose a new password.
     * On success, isFirstLogin is set to false.
     *
     * POST /api/v1/auth/reset-password
     */
    @PostMapping("/reset-password")
    public ResponseEntity<MessageResponse> resetPassword(
            @RequestBody ResetPasswordRequest request) {
        return ResponseEntity.ok(authService.resetPassword(request));
    }

    /**
     * Logout — blacklists the current JWT so it is rejected immediately,
     * even before its natural expiry time.
     * When the token expires on its own, the user is automatically logged out
     * (401 returned by the security filter).
     *
     * POST /api/v1/auth/logout
     * Header: Authorization: Bearer <token>
     */
    @PostMapping("/logout")
    public ResponseEntity<MessageResponse> logout(
            @RequestHeader(value = "Authorization", required = false) String bearerHeader) {
        return ResponseEntity.ok(authService.logout(bearerHeader));
    }
}