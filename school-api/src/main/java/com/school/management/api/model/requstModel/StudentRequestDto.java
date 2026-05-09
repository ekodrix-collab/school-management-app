package com.school.management.api.model.requstModel;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
public class StudentRequestDto {

    @JsonProperty(value = "first_name")
    private String firstName;

    @JsonProperty(value = "last_name")
    private String lastName;

    @JsonProperty(value = "admission_number")
    private Long admissionNumber;

    @JsonProperty(value = "first_language")
    private String firstLanguage;

    @JsonProperty(value = "second_language")
    private String secondLanguage;

    @JsonProperty(value="date_of_birth")
    private LocalDate dateOfBirth;

    @JsonProperty(value ="gender")
    private String gender;

    @JsonProperty(value ="blood_group")
    private String bloodGroup;

    @JsonProperty(value = "adhar_no")
    private String adharNo;

    @JsonProperty(value = "parent_id")
    private UUID parentId;

}
