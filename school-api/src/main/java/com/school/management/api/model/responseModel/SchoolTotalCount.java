package com.school.management.api.model.responseModel;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class SchoolTotalCount {

    @JsonProperty("school_count")
    private Long schoolCount;

    @JsonProperty("current_month_school_growth")
    private Long currentMonthSchoolGrowth;

}
