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

    @JsonProperty(value = "admission_number")
    private String admissionNumber;

    @JsonProperty(value = "roll_number")
    private String rollNumber;

    @JsonProperty(value = "date_of_birth")
    private LocalDate dateOfBirth;

    @JsonProperty(value = "gender")
    private String gender;

    @JsonProperty(value = "blood_group")
    private String bloodGroup;


}
