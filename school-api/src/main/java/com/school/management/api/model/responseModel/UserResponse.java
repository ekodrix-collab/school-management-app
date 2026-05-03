package com.school.management.api.model.responseModel;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.UUID;

@Data
public class UserResponse {

    @JsonProperty(value = "name")
    private String name;

    @JsonProperty(value = "mail")
    private String mail;

    @JsonProperty(value = "role")
    private String role;

    @JsonProperty(value = "number")
    private String number;

    @JsonProperty(value = "user_id")
    private UUID userId;

}
