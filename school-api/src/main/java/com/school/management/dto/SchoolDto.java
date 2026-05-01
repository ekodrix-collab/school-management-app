package com.school.management.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SchoolDto {
    private Long id;
    private String schoolCode;
    private String name;
    private String address;
    private String contactNumber;
    private String email;
    private boolean active;
    private String subscriptionType;
}
