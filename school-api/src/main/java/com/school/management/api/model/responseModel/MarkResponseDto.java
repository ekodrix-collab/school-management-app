package com.school.management.api.model.responseModel;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class MarkResponseDto {

    private Long id;

    @JsonProperty("student_id")
    private String studentId;

    @JsonProperty("class_id")
    private String classId;

    @JsonProperty("subject_id")
    private String subjectId;

    @JsonProperty("exam_id")
    private String examId;

    @JsonProperty("academic_year_id")
    private String academicYearId;

    private Integer marks;

    @JsonProperty("max_marks")
    private Integer maxMarks;

    private String grade;

    private String remarks;

    @JsonProperty("created_at")
    private LocalDateTime createdAt;

    @JsonProperty("updated_at")
    private LocalDateTime updatedAt;

}
