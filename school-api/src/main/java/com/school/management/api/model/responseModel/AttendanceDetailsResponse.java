package com.school.management.api.model.responseModel;

import com.fasterxml.jackson.annotation.JsonProperty;
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

    @JsonProperty("attendance_session_id")
    private String attendanceSessionId;

    @JsonProperty("school_id")
    private String schoolId;

    @JsonProperty("academic_year_id")
    private String academicYearId;

    @JsonProperty("class_id")
    private String classId;

    @JsonProperty("class_subject_id")
    private String classSubjectId;

    @JsonProperty("timetable_id")
    private String timetableId;

    @JsonProperty("teacher_id")
    private UUID teacherId;

    @JsonProperty("attendance_date")
    private LocalDate attendanceDate;

    @JsonProperty("session_type")
    private String sessionType;

    @JsonProperty("period_number")
    private Integer periodNumber;

    @JsonProperty("is_substitution")
    private Boolean isSubstitution;

    @JsonProperty("original_class_subject_id")
    private String originalClassSubjectId;

    @JsonProperty("original_teacher_id")
    private UUID originalTeacherId;

    @JsonProperty("remarks")
    private String remarks;

    @JsonProperty("created_at")
    private LocalDateTime createdAt;

    @JsonProperty("updated_at")
    private LocalDateTime updatedAt;

    @JsonProperty("students")
    private List<StudentAttendanceResponse> students;

}