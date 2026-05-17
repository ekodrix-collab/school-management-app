package com.school.management.api.model.responseModel;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.UUID;

@Data
public class ParentResponse {

    @JsonProperty(value = "name")
    private String name;

    @JsonProperty(value = "email")
    private String email;

    @JsonProperty(value = "parent_id")
    private UUID parentId;

    @JsonProperty(value = "is_active")
    private Boolean isActive;

    @JsonProperty(value = "address")
    private AddressResponse address;

}
