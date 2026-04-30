package com.school.management.api.model.requstModel;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import lombok.Data;

import java.time.LocalDate;

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

    @JsonProperty(value ="gender")
    private String gender;

    @JsonProperty(value ="blood_group")
    private String bloodGroup;
}
