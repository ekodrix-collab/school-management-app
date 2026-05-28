package com.school.management.api.model.requstModel;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AdmissionRequest {

    @JsonProperty("class_id")
    private String classId;

    @JsonProperty("academic_year_id")
    private String academicYearId;

    @JsonProperty("roll_number")
    private Integer rollNumber;

    @JsonProperty("status")
    private String status;

    @JsonProperty("student_details")
    private StudentRequestDto studentDetails;

    @JsonProperty("user_details")
    private UserRequestDto userDetails;

    @JsonProperty("parent_details")
    private ParentRequestDto parentDetails;

    @JsonProperty("address_details")
    private AddressRequest addressDetails;

}