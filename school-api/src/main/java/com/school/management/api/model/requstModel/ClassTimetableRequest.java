package com.school.management.api.model.requstModel;

import lombok.Data;

import java.time.LocalTime;
import java.util.UUID;

@Data
public class ClassTimetableRequest {

    private String schoolId;

    private String academicYearId;

    private String classId;

    private String classSubjectId;

    private UUID teacherId;

    private String dayName;

    private Integer periodNumber;

    private LocalTime startTime;

    private LocalTime endTime;

    private Boolean isBreak;

}
