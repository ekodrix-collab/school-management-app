package com.school.management.api.model.responseModel;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
public class ExamSubjectResponse {

    private String examSubjectId;

    private String schoolId;

    private String academicYearId;

    private String examId;

    private String classId;

    private String classSubjectId;

    private LocalDate examDate;

    private LocalTime startTime;

    private LocalTime endTime;

    private Integer maxMark;

    private Integer passMark;

    private String status;

    private String remarks;

    private Boolean isActive;

    private LocalDateTime createdAt;

}