package com.school.management.api.model.responseModel;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AdminResponse {

    @JsonProperty(value = "name")
    private String name;

    @JsonProperty(value = "mail")
    private String mail;

    @JsonProperty(value = "role")
    private String role;

    @JsonProperty(value = "number")
    private String number;
}
