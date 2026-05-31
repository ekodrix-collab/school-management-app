package com.school.management.api.model.responseModel;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class TeacherTotalCount {

    @JsonProperty("teacher_count")
    private Long teacherCount;

    @JsonProperty("current_month_teacher_growth")
    private Long currentMonthTeacherGrowth;

}
