package com.school.management.api.model.responseModel;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ClassTimetableResponse {

    private String timetableId;

    private String message;
}