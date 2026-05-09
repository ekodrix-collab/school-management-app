package com.school.management.api.model.requstModel;

import lombok.Data;

@Data
public class AdmissionRequest {

    private String classId;
    private String academicYearId;
    private Integer rollNumber;
    private String status;

    private StudentRequestDto studentDetails;
    private UserRequestDto userDetails;
    private ParentRequestDto parentDetails;
    private AddressRequest addressDetails;

}
