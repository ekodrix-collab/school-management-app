package com.school.management.api.model.requstModel;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ExamRequest {

    private String academicYearId;

    private String examName;

    private String examType;

    private LocalDate startDate;

    private LocalDate endDate;

    private LocalDate resultPublishDate;

    private String remarks;

}
