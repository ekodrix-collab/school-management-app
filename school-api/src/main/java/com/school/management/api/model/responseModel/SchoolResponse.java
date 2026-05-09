package com.school.management.api.model.responseModel;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.school.management.api.model.requstModel.AddressRequest;
import lombok.Data;

@Data
public class SchoolResponse {

    @JsonProperty(value = "school_id")
    private String schoolId;

    @JsonProperty(value = "email")
    private String email;

    @JsonProperty(value = "phone")
    private String phone;

    @JsonProperty(value = "address")
    private AddressResponse address;

}
