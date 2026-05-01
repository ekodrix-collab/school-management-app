package com.school.management.controller;

import com.school.management.dto.ApiResponse;
import com.school.management.dto.CreateUserRequest;
import com.school.management.dto.UserDto;
import com.school.management.service.UserService;
import com.school.management.tenant.TenantContext;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'SCHOOL_ADMIN')")
    public ResponseEntity<ApiResponse<UserDto>> createUser(@RequestBody CreateUserRequest request) {
        return ResponseEntity.ok(ApiResponse.success("User created successfully", 
                userService.createUser(request.getUser(), request.getPassword())));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserDto>> getUser(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success("User fetched successfully", userService.getUserById(id)));
    }

    @GetMapping("/school")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'SCHOOL_ADMIN')")
    public ResponseEntity<ApiResponse<List<UserDto>>> getUsersBySchool() {
        Long schoolId = TenantContext.getCurrentTenant();
        return ResponseEntity.ok(ApiResponse.success("Users fetched successfully", userService.getAllUsersBySchool(schoolId)));
    }
}
