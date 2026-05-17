package com.school.management.api.model.responseModel;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.UUID;

@Data
public class OnBoardResponse {

    @JsonProperty(value = "success")
    private Boolean success;

    @JsonProperty(value = "message")
    private String message;

    @JsonProperty("name")
    private String name;

    @JsonProperty(value = "role")
    private String role;

    @JsonProperty(value = "techer_id")
    private UUID teacherId;

    @JsonProperty(value = "is_first_login")
    private Boolean isFirstLogin;

}
