package com.school.management.api.model.requstModel;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class SchoolClassRequest {

    @JsonProperty(value = "standard")
    private String standard;        // "1", "LKG"

    @JsonProperty(value = "division")
    private String division;

    @JsonProperty(value = "class_teacher_id")
    private String classTeacherId;

    @JsonProperty(value = "capacity")
    private Integer capacity;

}
