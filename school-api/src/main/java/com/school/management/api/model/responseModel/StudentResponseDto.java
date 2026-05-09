package com.school.management.api.model.responseModel;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class StudentResponseDto {

    @JsonProperty(value = "name")
    private String name;

    @JsonProperty(value = "student_id")
    private String studentId;

    @JsonProperty(value = "admission_number")
    private Long admissionNumber;

    @JsonProperty(value = "date_of_birth")
    private LocalDate dateOfBirth;

    @JsonProperty(value = "first_language")
    private String firstLanguage;

    @JsonProperty(value = "second_language")
    private String secondLanguage;

    @JsonProperty(value = "gender")
    private String gender;

    @JsonProperty(value = "blood_group")
    private String bloodGroup;


}
