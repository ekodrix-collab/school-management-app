package com.school.management.api.model.responseModel;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class StudentMarkResponse {

    private String studentMarkId;

    private String schoolId;

    private String academicYearId;

    private String examId;

    private String examSubjectId;

    private String classId;

    private String studentId;

    private Double obtainedMark;

    private Double percentage;

    private String grade;

    private String resultStatus;

    private String attendanceStatus;

    private String remarks;

    private Boolean isPublished;

    private LocalDateTime createdAt;

}