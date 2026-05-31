package com.school.management.api.model.responseModel;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class TotalCountResponse {

    @JsonProperty("school_count")
    private Long schoolCount;

    @JsonProperty("student_count")
    private Long studentCount;

    @JsonProperty("teacher_count")
    private Long teacherCount;

    @JsonProperty("revenue")
    private Double revenue;

    @JsonProperty("current_month_school_growth")
    private Long currentMonthSchoolGrowth;

    @JsonProperty("current_month_student_growth")
    private Long currentMonthStudentGrowth;

    @JsonProperty("current_month_teacher_growth")
    private Long currentMonthTeacherGrowth;

    @JsonProperty("current_month_revenue")
    private Double currentMonthRevenue;

}
