package com.school.management.api.model.responseModel;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;



@Data
public class RevenueTotalCount {

    @JsonProperty("revenue")
    private Double revenue = 0.0;

    @JsonProperty("current_month_revenue")
    private Double currentMonthRevenue = 0.0;

}
