package com.school.management.api.model.requstModel;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class SchoolRequestDto {

    @JsonProperty(value = "school_name")
    private String schoolName;

    @JsonProperty(value = "school_code")
    private String schoolCode;

    @JsonProperty(value = "email")
    private String email;

    @JsonProperty(value = "phone")
    private String phone;

    @JsonProperty(value = "address")
    private String address;

    @JsonProperty(value = "city")
    private String city;

    @JsonProperty(value = "state")
    private String state;

    @JsonProperty(value = "country")
    private String country;

    @JsonProperty(value = "pincode")
    private String pincode;

    @JsonProperty(value = "logo_url")
    private String logoUrl;

    @JsonProperty(value = "admin")
    private AdminRequestDto admin;
}
