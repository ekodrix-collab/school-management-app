package com.school.management.api.model.responseModel;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class StudentTotalCount {

    @JsonProperty("student_count")
    private Long studentCount;

    @JsonProperty("current_month_student_growth")
    private Long currentMonthStudentGrowth;
}
