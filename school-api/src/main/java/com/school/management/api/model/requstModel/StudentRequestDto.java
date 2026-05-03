package com.school.management.api.model.requstModel;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
public class StudentRequestDto {

    @JsonProperty(value = "name")
    private String name;

    @JsonProperty(value = "admission_number")
    private String admissionNumber;

    @JsonProperty(value ="roll_number")
    private String rollNumber;

    @JsonProperty(value="date_of_birth")
    private LocalDate dateOfBirth;

    @JsonProperty(value = "class_id")
    private String classId;

    @JsonProperty(value = "school_id")
    private String schoolId;

    @JsonProperty(value ="gender")
    private String gender;

    @JsonProperty(value ="blood_group")
    private String bloodGroup;

    @JsonProperty(value = "user_details")
    private UserRequestDto userDetails;

    @JsonProperty(value = "parent_details")
    private ParentRequestDto parentDetails;

    @JsonProperty(value = "user_id")
    private UUID userId;
}
