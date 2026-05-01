package com.school.management.dto;

import com.school.management.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Long id;
    private UUID userId;
    private String name;
    private String username;
    private String email;
    private String mobile;
    private User.Role role;
    private Long schoolId;
}
