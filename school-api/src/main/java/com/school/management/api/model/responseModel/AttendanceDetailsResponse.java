package com.school.management.api.model.responseModel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AttendanceDetailsResponse {

    private String attendanceSessionId;
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
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<StudentAttendanceResponse> students;

}
