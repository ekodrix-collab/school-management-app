package com.school.management.api.model.requstModel;

import lombok.Data;

@Data
public class StudentMarkRequest {

    private String academicYearId;

    private String examId;

    private String examSubjectId;

    private String classId;

    private String studentId;

    private Double obtainedMark;

    private String attendanceStatus;

    private String remarks;

}