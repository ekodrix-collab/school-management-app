package com.school.management.api.model.responseModel;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class TeacherResponseDto {

    @JsonProperty(value = "teacher_id")
    private UUID teacherId;

    @JsonProperty(value = "name")
    private String name;

    @JsonProperty(value = "email")
    private String email;

    @JsonProperty(value = "mobile")
    private String mobile;

    @JsonProperty(value = "subject")
    private List<String> subject;

    @JsonProperty(value = "classes")
    private List<String> classes;

    @JsonProperty(value = "is_active")
    private Boolean isActive;

    @JsonProperty(value = "role")
    private String role;
}
