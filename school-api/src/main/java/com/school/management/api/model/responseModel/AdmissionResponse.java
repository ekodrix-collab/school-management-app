package com.school.management.api.model.responseModel;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AdmissionResponse {

    @JsonProperty("name")
    private String name;

    @JsonProperty("class_name")
    private String className;

    @JsonProperty("admission_number")
    private Long admissionNumber;

    @JsonProperty("admission_id")
    private String admissionId;

    @JsonProperty("gender")
    private String gender;

    @JsonProperty("first_language")
    private String firstLanguage;

    @JsonProperty("second_language")
    private String secondLanguage;

    @JsonProperty("status")
    private String status;

}
