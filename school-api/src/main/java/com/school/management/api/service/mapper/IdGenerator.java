package com.school.management.api.service.mapper;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class IdGenerator {

    public static String generateStudentId(String value) {
        return value + "-" + UUID.randomUUID().toString().substring(0, 12);
    }
}
