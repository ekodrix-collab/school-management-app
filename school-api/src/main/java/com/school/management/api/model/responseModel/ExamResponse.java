package com.school.management.api.model.responseModel;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class ExamResponse  {

    private String examId;

    private String schoolId;

    private String academicYearId;

    private String examName;

    private String examType;

    private LocalDate startDate;

    private LocalDate endDate;

    private LocalDate resultPublishDate;

    private String status;

    private String remarks;

    private Boolean isActive;

    private LocalDateTime createdAt;

}
