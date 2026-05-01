package com.school.management.dto;

import lombok.Data;

@Data
public class CreateUserRequest {
    private UserDto user;
    private String password;
}
