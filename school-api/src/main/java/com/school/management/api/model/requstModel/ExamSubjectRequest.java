package com.school.management.api.model.requstModel;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class ExamSubjectRequest {

    private String academicYearId;

    private String examId;

    private String classId;

    private String classSubjectId;

    private LocalDate examDate;

    private LocalTime startTime;

    private LocalTime endTime;

    private Integer maxMark;

    private Integer passMark;

    private String remarks;

}