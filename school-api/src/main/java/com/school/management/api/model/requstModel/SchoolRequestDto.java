package com.school.management.api.model.requstModel;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class SchoolRequestDto {

    @JsonProperty(value = "school_id")
    private String schoolId;

    @JsonProperty(value = "email")
    private String email;

    @JsonProperty(value = "phone")
    private String phone;

    @JsonProperty(value = "address")
    private AddressRequest address;

}
