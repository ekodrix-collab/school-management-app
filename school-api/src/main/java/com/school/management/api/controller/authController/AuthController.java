package com.school.management.api.controller.authController;


import com.school.management.api.constants.Constants;
import com.school.management.api.model.requstModel.OnboardRequest;
import com.school.management.api.model.responseModel.AuthResponse;
import com.school.management.api.model.requstModel.LoginRequest;
import com.school.management.api.model.requstModel.RegisterRequest;
import com.school.management.api.service.authService.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

}