package com.school.management.api.model.requstModel;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
public class AttendanceRequest {

    private String schoolId;

    private String academicYearId;

    private String classId;

    private String classSubjectId;

    private String timetableId;

    private UUID teacherId;

    private LocalDate attendanceDate;

    private String sessionType;

    private Integer periodNumber;

    private Boolean isSubstitution;

    private String originalClassSubjectId;

    private UUID originalTeacherId;

    private String remarks;

    private List<AttendanceStudentRequest> students;
}