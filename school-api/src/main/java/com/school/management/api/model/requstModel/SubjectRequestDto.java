package com.school.management.api.model.requstModel;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class SubjectRequestDto {

    @JsonProperty(value = "name")
    private String name;

}
