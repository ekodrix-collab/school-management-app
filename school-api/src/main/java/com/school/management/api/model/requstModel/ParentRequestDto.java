package com.school.management.api.model.requstModel;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class ParentRequestDto {

    @JsonProperty(value = "name")
    private String name;

    @JsonProperty(value = "parent_id")
    private UUID parentId;

    @JsonProperty(value = "school_id")
    private String schoolId;

    @JsonProperty(value = "address_id")
    private String addressId;

    @JsonProperty(value = "mobile")
    private String mobile;

    @JsonProperty(value = "alternate_mobile")
    private String alternateMobile;

    @JsonProperty(value = "role")
    private String role;

}
