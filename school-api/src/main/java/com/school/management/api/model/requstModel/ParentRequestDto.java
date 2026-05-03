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

    @JsonProperty(value = "spouse_name")
    private String spouseName;

    @JsonProperty(value = "mobile")
    private String mobile;

    @JsonProperty(value = "student_ids")
    private List<String> studentIds;

    @JsonProperty(value = "alternate_mobile")
    private String alternateMobile;

    @JsonProperty(value = "email")
    private String email;

    @JsonProperty(value = "house_name")
    private String houseName;

    @JsonProperty(value = "place")
    private String place;

    @JsonProperty(value = "city")
    private String city;

    @JsonProperty(value = "district")
    private String district;

    @JsonProperty(value = "state")
    private String state;

    @JsonProperty(value = "pincode")
    private String pincode;

    @JsonProperty(value = "landmark")
    private String landmark;

}
